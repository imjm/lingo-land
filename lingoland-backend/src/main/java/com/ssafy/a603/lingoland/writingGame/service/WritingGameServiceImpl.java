package com.ssafy.a603.lingoland.writingGame.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a603.lingoland.fairyTale.entity.FairyTale;
import com.ssafy.a603.lingoland.fairyTale.service.FairyTaleService;
import com.ssafy.a603.lingoland.global.error.entity.ErrorCode;
import com.ssafy.a603.lingoland.global.error.exception.BaseException;
import com.ssafy.a603.lingoland.global.error.exception.InvalidInputException;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.util.ImgUtils;
import com.ssafy.a603.lingoland.writingGame.dto.DrawingRequestDTO;
import com.ssafy.a603.lingoland.writingGame.dto.KarloDTO;
import com.ssafy.a603.lingoland.writingGame.dto.KarloReturn;
import com.ssafy.a603.lingoland.writingGame.dto.OllamaStoryDTO;
import com.ssafy.a603.lingoland.writingGame.dto.OllamaStoryResponseDTO;
import com.ssafy.a603.lingoland.writingGame.dto.OllamaSummaryDTO;
import com.ssafy.a603.lingoland.writingGame.dto.OllamaSummaryResponseDTO;
import com.ssafy.a603.lingoland.writingGame.dto.WritingGameStartRequestDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WritingGameServiceImpl implements WritingGameService {
	private static final String FAIRY_TALE_IMAGE_PATH = "fairyTale";
	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;
	private final ConcurrentHashMap<String, List<DrawingRequestDTO>> requestMap = new ConcurrentHashMap<>();
	private final RestClient restClient;
	private final FairyTaleService fairyTaleService;
	private final ImgUtils imgUtils;

	@Qualifier("myExecutor")
	private final ExecutorService executor;

	@Value("${kakao.api.key}")
	private String imageApiKey;

	@Value("${ollama.api-url}")
	private String ollamaUrl;

	@Override
	public int[] start(String sessionId, WritingGameStartRequestDTO request) {
		log.info("Starting game for session: {}", sessionId);
		String redisKey = "lingoland:fairyTale:session:" + sessionId;
		serializeToRedis(redisKey, request);
		int[] randomList = randomNumList(request.numPart());
		log.debug("Generated random list: {} for session: {}", Arrays.toString(randomList), sessionId);
		return randomList;
	}

	@Override
	public void setTitle(String title, CustomUserDetails customUserDetails) {
		String redisKey = "lingoland:fairyTale:" + customUserDetails.getUsername() + ":title";
		serializeToRedis(redisKey, title);
	}

	@Override
	public List<FairyTale> submitStory(String sessionId, DrawingRequestDTO dto) {
		log.info("Submitting story for session: {}", sessionId);
		List<DrawingRequestDTO> requests = requestMap.compute(sessionId, (key, existingList) -> {
			if (existingList == null) {
				existingList = new ArrayList<>();
			}
			existingList.add(dto);
			log.debug("Added DrawingRequestDTO to session: {}, current size: {}", sessionId, existingList.size());
			return existingList;
		});

		WritingGameStartRequestDTO sessionInfo = deserializeFromRedis("lingoland:fairyTale:session:" + sessionId,
			WritingGameStartRequestDTO.class);
		if (requests.size() == sessionInfo.numPart()) {
			log.info("All members have submitted stories for session: {}", sessionId);
			List<DrawingRequestDTO> collected = new ArrayList<>(requests);
			requestMap.remove(sessionId);

			CompletableFuture<List<FairyTale>> future = processStoriesAsync(sessionId, collected, sessionInfo)
				.thenCompose(voidResult -> {
					if (requests.get(0).order() == sessionInfo.maxTurn()) {
						log.info("Final tasks after all stories are processed for session: {}", sessionId);
						return CompletableFuture.supplyAsync(() -> end(collected), executor);
					} else {
						return CompletableFuture.completedFuture(Collections.emptyList());
					}
				});
			log.debug("Returning processed stories for session: {}", sessionId);
			return requests.get(0).order() == sessionInfo.maxTurn() ? future.join() : Collections.emptyList();
		}
		return Collections.emptyList();
	}

	@Async("asyncExecutor")
	private CompletableFuture<Void> processStoriesAsync(String sessionId, List<DrawingRequestDTO> requests,
		WritingGameStartRequestDTO sessionInfo) {
		log.info("Processing stories asynchronously for session: {}", sessionId);
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (DrawingRequestDTO request : requests) {
			log.debug("Processing single story for session: {}, order: {}", sessionId, request.order());
			futures.add(CompletableFuture.runAsync(() -> processSingleStory(sessionId, request), executor));
		}
		return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
			.thenRun(() -> log.info("All story processing tasks completed for session: {}", sessionId));
	}

	private void processSingleStory(String sessionId, DrawingRequestDTO request) {
		log.info("Processing single story: {} for session: {}", request.key(), sessionId);
		String story = request.story();
		String imgUrl = handleStoryTranslation(story);
		String savedImgUrl = null;
		if (imgUrl.equals(imgUtils.getDefaultImage()))
			savedImgUrl = imgUtils.getImagePathWithDefaultImage(FAIRY_TALE_IMAGE_PATH);
		else
			savedImgUrl = imgUtils.saveBase64Image(imgUrl, FAIRY_TALE_IMAGE_PATH);
		log.debug("Story image saved at: {}", savedImgUrl);
		saveStoryToRedis(request.key(), request, savedImgUrl);
	}

	private String handleStoryTranslation(String story) {
		log.info("Translating story to image prompt");
		OllamaStoryResponseDTO translated = null;
		try {
			translated = translateStory2ImagePrompt(story);
		} catch (Exception e) {
			log.warn("Skipping image generation due to title creation failure.");
			return imgUtils.getDefaultImage();
		}
		if (translated == null || translated.medium().isBlank()) {
			log.warn("Skipping image generation due to title creation failure.");
			return imgUtils.getDefaultImage();
		}
		return generateImageWithFallback(translated.toString());
	}

	private String generateImageWithFallback(String translated) {
		log.info("Generating image with fallback for translated text.");
		try {
			return generateImage(translated);
		} catch (Exception e) {
			log.error("Image generation failed, using default image", e);
			return imgUtils.getDefaultImage();
		}
	}

	private void saveStoryToRedis(String key, DrawingRequestDTO request, String savedImgUrl) {
		log.info("Saving story to Redis with key: {}", key);
		String redisStoryKey = "lingoland:fairyTale:" + key;
		FairyTale.Story node = FairyTale.Story.builder()
			.illustration(savedImgUrl)
			.story(request.story())
			.build();

		try {
			if (request.order() == 1) {
				List<FairyTale.Story> stories = new ArrayList<>();
				stories.add(node);
				serializeToRedis(redisStoryKey, stories);
				log.debug("Serialized new story to Redis for key: {}", redisStoryKey);
				return;
			}
			List<FairyTale.Story> existingStories = getExistingStories(redisStoryKey);
			existingStories.add(node);
			serializeToRedis(redisStoryKey, existingStories);
			log.debug("Serialized updated story to Redis for key: {}", redisStoryKey);
		} catch (JsonProcessingException e) {
			log.error("Error processing JSON for Redis serialization", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
	}

	private List<FairyTale.Story> getExistingStories(String redisStoryKey) throws JsonProcessingException {
		String existingJson = (String)redisTemplate.opsForValue().get(redisStoryKey);
		return existingJson != null ? objectMapper.readValue(existingJson, new TypeReference<List<FairyTale.Story>>() {
		}) : new ArrayList<>();
	}

	@Async("asyncExecutor")
	private List<FairyTale> end(List<DrawingRequestDTO> requests) {
		log.info("Ending game session with {} requests", requests.size());

		List<String> writers = requests.stream()
			.map(DrawingRequestDTO::key)
			.collect(Collectors.toList());
		log.debug("Writers involved in this session: {}", writers);

		List<CompletableFuture<FairyTale>> futures = requests.stream()
			.map(request -> CompletableFuture.supplyAsync(() -> {
					log.info("Processing end of session for request: {}", request.key());
					return endProcess(request, writers);
				}, executor)
				.exceptionally(ex -> {
					log.error("Error processing request: {}", request.key(), ex);
					throw new InvalidInputException(ErrorCode.INTERNAL_SERVER_ERROR);
				}))
			.collect(Collectors.toList());

		log.info("Waiting for all end-process tasks to complete");
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

		List<FairyTale> fairyTales = futures.stream()
			.map(CompletableFuture::join)
			.collect(Collectors.toList());
		log.info("Completed end-process tasks for all requests");

		return fairyTales;
	}

	private FairyTale endProcess(DrawingRequestDTO request, List<String> writers) {
		String redisStoryKey = "lingoland:fairyTale:" + request.key();
		log.info("Retrieving and processing stories from Redis with key: {}", redisStoryKey);

		List<FairyTale.Story> stories;
		try {
			String existingJson = (String)redisTemplate.opsForValue().get(redisStoryKey);
			stories = objectMapper.readValue(existingJson, new TypeReference<List<FairyTale.Story>>() {
			});
			log.debug("Retrieved {} stories from Redis for key: {}", stories.size(), redisStoryKey);
		} catch (JsonProcessingException e) {
			log.error("Error processing JSON from Redis for key: {}", redisStoryKey, e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}

		StringBuilder allStories = new StringBuilder();
		for (FairyTale.Story story : stories) {
			allStories.append(story.getStory()).append("\n");
		}
		log.debug("Combined all stories for processing: {}", allStories.toString());

		OllamaSummaryResponseDTO titleWithSummary;
		String imgUrl;
		String savedImgUrl;

		try {
			titleWithSummary = makeTitleWithSummary(allStories.toString());
			log.info("Generated title and summary: {}", titleWithSummary);
		} catch (Exception e) {
			log.error("Failed to create title and summary, using default values", e);
			titleWithSummary = new OllamaSummaryResponseDTO("No title", "No summary",
				OllamaStoryResponseDTO.builder().build());
		}

		boolean isValidTitle = titleWithSummary != null && titleWithSummary.title() != null && !titleWithSummary.title()
			.equals("No title");
		boolean isValidSummary = titleWithSummary.summary() != null && !titleWithSummary.summary().equals("No summary");
		boolean isValidContent = titleWithSummary.content() != null;

		if (!isValidTitle || !isValidSummary || !isValidContent) {
			log.warn("Skipping image generation due to invalid title or summary.");
			savedImgUrl = imgUtils.getImagePathWithDefaultImage(FAIRY_TALE_IMAGE_PATH);
		} else {
			imgUrl = generateImageWithFallback(titleWithSummary.content().toString());
			savedImgUrl = imgUtils.saveBase64Image(imgUrl, FAIRY_TALE_IMAGE_PATH);
			log.debug("Saved generated image at: {}", savedImgUrl);
		}

		String title = deserializeFromRedis("lingoland:fairyTale:" + request.key() + ":title", String.class);

		FairyTale fairyTale = fairyTaleService.createFairyTale(title, savedImgUrl,
			titleWithSummary.summary(), stories, writers);
		log.info("Fairy tale created with title: {}", titleWithSummary.title());

		return fairyTale;
	}

	private OllamaSummaryResponseDTO makeTitleWithSummary(String story) {
		log.info("Summary story using llama3.1 : {}", story);
		String json = restClient.post()
			.uri(ollamaUrl)
			.body(new OllamaSummaryDTO(story))
			.retrieve()
			.body(String.class);
		try {
			JsonNode jsonNode = objectMapper.readTree(json);
			String response = jsonNode.get("response").asText().trim();
			log.info("Summary result: {}", response);
			return objectMapper.readValue(response, new TypeReference<OllamaSummaryResponseDTO>() {
			});
		} catch (JsonProcessingException e) {
			log.error("Failed to process JSON response from translation API", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
	}

	private OllamaStoryResponseDTO translateStory2ImagePrompt(String story) {
		log.info("Translating story using llama3.1 : {}", story);
		String json = restClient.post()
			.uri(ollamaUrl)
			.body(new OllamaStoryDTO(story))
			.retrieve()
			.body(String.class);
		try {
			JsonNode jsonNode = objectMapper.readTree(json);
			String response = jsonNode.get("response").asText();
			log.info("Translation result: {}", response);
			return objectMapper.readValue(response, new TypeReference<OllamaStoryResponseDTO>() {
			});
		} catch (JsonProcessingException e) {
			log.error("Failed to process JSON response from translation API", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
	}

	private String generateImage(String translated) {
		log.info("generate image using text {}", translated);
		KarloReturn karloReturn = restClient.post()
			.uri("https://api.kakaobrain.com/v2/inference/karlo/t2i")
			.header("Authorization", "KakaoAK " + imageApiKey)
			.header("Content-Type", "application/json")
			.body(new KarloDTO(translated))
			.retrieve()
			.body(KarloReturn.class);

		if (karloReturn != null && karloReturn.images().length > 0) {
			log.info("image seed : {}", karloReturn.images()[0].seed());
			return karloReturn.images()[0].image();
		}
		log.error("Failed to generate image for translated text: {}", translated);
		throw new InvalidInputException(ErrorCode.INVALID_INPUT_VALUE);
	}

	private <T> void serializeToRedis(String key, T value) {
		try {
			redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(value));
		} catch (JsonProcessingException e) {
			log.error("Failed to serialize object to Redis", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
	}

	private <T> T deserializeFromRedis(String key, Class<T> valueType) {
		String json = (String)redisTemplate.opsForValue().get(key);
		try {
			return objectMapper.readValue(json, valueType);
		} catch (JsonProcessingException e) {
			log.error("Failed to deserialize object from Redis", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
	}

	private int[] randomNumList(int n) {
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = i + 1;
		}
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		for (int i = array.length - 1; i > 0; i--) {
			int j = rand.nextInt(i + 1);
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return array;
	}
}

