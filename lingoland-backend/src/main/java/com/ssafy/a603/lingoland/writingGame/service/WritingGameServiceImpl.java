package com.ssafy.a603.lingoland.writingGame.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
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
import com.ssafy.a603.lingoland.util.ImgUtils;
import com.ssafy.a603.lingoland.writingGame.dto.AllamaStoryDTO;
import com.ssafy.a603.lingoland.writingGame.dto.AllamaStoryResponseDTO;
import com.ssafy.a603.lingoland.writingGame.dto.AllamaSummaryDTO;
import com.ssafy.a603.lingoland.writingGame.dto.AllamaSummaryResponseDTO;
import com.ssafy.a603.lingoland.writingGame.dto.DrawingRequestDTO;
import com.ssafy.a603.lingoland.writingGame.dto.KarloDTO;
import com.ssafy.a603.lingoland.writingGame.dto.KarloReturn;
import com.ssafy.a603.lingoland.writingGame.dto.WritingGameStartRequestDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WritingGameServiceImpl implements WritingGameService {
	private static final String FAIRY_TALE_IMAGE_PATH = "fairyTale";
	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;
	private final ConcurrentHashMap<String, List<DrawingRequestDTO>> requestMap;
	private final RestClient restClient;
	private final String restApiKey;
	private final FairyTaleService fairyTaleService;
	private final ImgUtils imgUtils;

	public WritingGameServiceImpl(ObjectMapper objectMapper, RedisTemplate<String, Object> redisTemplate,
		@Value("${kakao.api.key}") String restApiKey, @Value("${deepL.api.key}") String deepLApiKey,
		FairyTaleService fairyTaleService, ImgUtils imgUtils) {
		this.objectMapper = objectMapper;
		this.redisTemplate = redisTemplate;
		this.requestMap = new ConcurrentHashMap<>();

		ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
			.withReadTimeout(Duration.ofMinutes(3));
		ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(settings);
		this.restClient = RestClient.builder()
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.defaultStatusHandler(HttpStatusCode::is4xxClientError,
				(request, response) -> {
					log.error("Client Error Code={}", response.getStatusCode());
					log.error("Client Error Message={}", new String(response.getBody().readAllBytes()));
				})
			.defaultStatusHandler(HttpStatusCode::is5xxServerError,
				(request, response) -> {
					log.error("Server Error Code={}", response.getStatusCode());
					log.error("Server Error Message={}", new String(response.getBody().readAllBytes()));
				})
			.requestFactory(requestFactory)
			.build();
		this.restApiKey = restApiKey;
		this.fairyTaleService = fairyTaleService;
		this.imgUtils = imgUtils;
	}

	@Override
	public int[] start(String sessionId, WritingGameStartRequestDTO request) {
		String redisKey = "lingoland:fairyTale:session:" + sessionId;
		log.info("Starting game for session: {}", sessionId);
		try {
			redisTemplate.opsForValue().set(redisKey, objectMapper.writeValueAsString(request));
		} catch (JsonProcessingException e) {
			log.error("Failed to serialize WritingGameStartRequestDTO", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
		return randomNumList(request.numPart());
	}

	@Override
	public List<FairyTale> submitStory(String sessionId, DrawingRequestDTO dto) {
		log.info("Submitting story for session: {}", sessionId);
		requestMap.putIfAbsent(sessionId, new ArrayList<>());
		List<DrawingRequestDTO> requests = requestMap.get(sessionId);
		requests.add(dto);
		log.debug("Added DrawingRequestDTO to session: {}, current size: {}", sessionId, requests.size());

		String sessionInfoJson = (String)redisTemplate.opsForValue().get("lingoland:fairyTale:session:" + sessionId);
		WritingGameStartRequestDTO sessionInfo;
		try {
			sessionInfo = objectMapper.readValue(sessionInfoJson, WritingGameStartRequestDTO.class);
		} catch (JsonProcessingException e) {
			log.error("Failed to deserialize WritingGameStartRequestDTO", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
		ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
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

			if (requests.get(0).order() == sessionInfo.maxTurn()) {
				return future.join();
			} else {
				return Collections.emptyList();
			}
		}
		return Collections.emptyList();
	}

	@Async("sampleExecutor")
	private CompletableFuture<Void> processStoriesAsync(String sessionId, List<DrawingRequestDTO> requests,
		WritingGameStartRequestDTO sessionInfo) {
		log.info("Processing stories asynchronously for session: {}", sessionId);
		ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (DrawingRequestDTO request : requests) {
			CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
				String story = request.story();

				String imgUrl = null;
				AllamaStoryResponseDTO translated = null;
				try {
					translated = translateStory2ImagePrompt(story);
					log.info("Translated story: {}", translated);
				} catch (BaseException e) {
					translated = AllamaStoryResponseDTO.builder().build();
					log.error("Failed to create title and summary, using default values");
				}

				if (translated != null && !translated.medium().isBlank()) {
					try {
						imgUrl = generateImage(translated.toString());
						log.info("Generated image URL: {}", imgUrl);
					} catch (BaseException e) {
						log.error("Image generation failed, using default image", e);
						imgUrl = imgUtils.getDefaultImage();
					}
				} else {
					log.warn("Skipping image generation due to title creation failure.");
					imgUrl = imgUtils.getDefaultImage(); // 기본 이미지 사용
				}

				String savedImgUrl = imgUtils.saveBase64Image(imgUrl, FAIRY_TALE_IMAGE_PATH);
				String redisStoryKey = "lingoland:fairyTale:" + request.key();
				FairyTale.Story node = FairyTale.Story.builder()
					.illustration(savedImgUrl)
					.story(request.story())
					.build();

				try {
					if (request.order() == 1) {
						List<FairyTale.Story> lists = new ArrayList<>();
						lists.add(node);
						redisTemplate.opsForValue().set(redisStoryKey, objectMapper.writeValueAsString(lists));
						log.info("Stored first story in Redis with key: {}", redisStoryKey);
					} else {
						String existingJson = (String)redisTemplate.opsForValue().get(redisStoryKey);
						List<FairyTale.Story> existingStories = objectMapper.readValue(existingJson,
							new TypeReference<List<FairyTale.Story>>() {
							});
						existingStories.add(node);
						redisTemplate.opsForValue()
							.set(redisStoryKey, objectMapper.writeValueAsString(existingStories));
						log.info("Updated story list in Redis with key: {}", redisStoryKey);
					}
				} catch (JsonProcessingException e) {
					log.error("Error processing JSON", e);
					throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
				}
			}, executor);
			futures.add(future);
		}
		return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
			.thenRun(() -> log.info("All story processing tasks completed for session: {}", sessionId));
	}

	@Async("sampleExecutor")
	private List<FairyTale> end(List<DrawingRequestDTO> requests) {
		log.info("Ending game session with requests: {}", requests);
		List<String> writers = requests.stream()
			.map(DrawingRequestDTO::key)
			.collect(Collectors.toList());

		ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
		List<CompletableFuture<FairyTale>> futures = requests.stream()
			.map(request -> CompletableFuture.supplyAsync(() -> endProcess(request, writers), executor)
				.exceptionally(ex -> {
					log.error("Error processing request: {}", request, ex);
					throw new InvalidInputException(ErrorCode.INTERNAL_SERVER_ERROR);
				}))
			.collect(Collectors.toList());
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
		return futures.stream()
			.map(CompletableFuture::join)
			.collect(Collectors.toList());
	}

	private FairyTale endProcess(DrawingRequestDTO request, List<String> writers) {
		String redisStoryKey = "lingoland:fairyTale:" + request.key();
		List<FairyTale.Story> stories;
		try {
			String existingJson = (String)redisTemplate.opsForValue().get(redisStoryKey);
			stories = objectMapper.readValue(existingJson, new TypeReference<List<FairyTale.Story>>() {
			});
		} catch (JsonProcessingException e) {
			log.error("Error processing JSON from Redis for key: {}", redisStoryKey, e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}

		StringBuilder allStories = new StringBuilder();
		for (FairyTale.Story story : stories) {
			allStories.append(story.getStory()).append("\n");
		}

		AllamaSummaryResponseDTO titleWithSummary = null;
		String imgUrl = null;

		try {
			titleWithSummary = makeTitleWithSummary(allStories.toString());
			log.info("Created Title: {}", titleWithSummary.toString());
		} catch (BaseException e) {
			log.error("Failed to create title and summary, using default values");
			titleWithSummary = new AllamaSummaryResponseDTO("No title", "No summary");
		}

		if (titleWithSummary != null && !titleWithSummary.title().equals("No title")) {
			try {
				imgUrl = generateImage(titleWithSummary.toString());
				log.info("Generated image URL: {}", imgUrl);
			} catch (BaseException e) {
				log.error("Image generation failed, using default image", e);
				imgUrl = imgUtils.getDefaultImage();
			}
		} else {
			log.warn("Skipping image generation due to title creation failure.");
			imgUrl = imgUtils.getDefaultImage(); // 기본 이미지 사용
		}

		String savedImgUrl = imgUtils.saveBase64Image(imgUrl, FAIRY_TALE_IMAGE_PATH);
		return fairyTaleService.createFairyTale(titleWithSummary.title(), savedImgUrl, titleWithSummary.summary(),
			stories, writers);
	}

	private AllamaSummaryResponseDTO makeTitleWithSummary(String story) throws BaseException {
		log.info("Summary story using Allama3.1 : {}", story);
		String json = restClient.post()
			.uri("http://localhost:11434/api/generate")
			.body(new AllamaSummaryDTO(story))
			.retrieve()
			.body(String.class);
		try {
			JsonNode jsonNode = objectMapper.readTree(json);
			String response = jsonNode.get("response").asText().trim();
			log.info("Summary result: {}", response);
			return objectMapper.readValue(response, new TypeReference<AllamaSummaryResponseDTO>() {
			});
		} catch (JsonProcessingException e) {
			log.error("Failed to process JSON response from translation API", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
	}

	private AllamaStoryResponseDTO translateStory2ImagePrompt(String story) throws BaseException {
		log.info("Translating story using Allama3.1 : {}", story);
		String json = restClient.post()
			.uri("http://localhost:11434/api/generate")
			.body(new AllamaStoryDTO(story))
			.retrieve()
			.body(String.class);
		try {
			JsonNode jsonNode = objectMapper.readTree(json);
			String response = jsonNode.get("response").asText();
			log.info("Translation result: {}", response);
			return objectMapper.readValue(response, new TypeReference<AllamaStoryResponseDTO>() {
			});
		} catch (JsonProcessingException e) {
			log.error("Failed to process JSON response from translation API", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
	}

	private String generateImage(String translated) throws BaseException {
		log.info("generate image using text {}", translated);
		KarloReturn karloReturn = restClient.post()
			.uri("https://api.kakaobrain.com/v2/inference/karlo/t2i")
			.header("Authorization", "KakaoAK " + restApiKey)
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
