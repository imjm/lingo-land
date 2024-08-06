package com.ssafy.a603.lingoland.writingGame.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a603.lingoland.fairyTale.entity.FairyTale;
import com.ssafy.a603.lingoland.fairyTale.service.FairyTaleService;
import com.ssafy.a603.lingoland.global.error.entity.ErrorCode;
import com.ssafy.a603.lingoland.global.error.exception.BaseException;
import com.ssafy.a603.lingoland.global.error.exception.InvalidInputException;
import com.ssafy.a603.lingoland.writingGame.dto.AllamaStoryDTO;
import com.ssafy.a603.lingoland.writingGame.dto.AllamaSummaryDTO;
import com.ssafy.a603.lingoland.writingGame.dto.AllamaSummaryResponseDTO;
import com.ssafy.a603.lingoland.writingGame.dto.DrawingRequestDTO;
import com.ssafy.a603.lingoland.writingGame.dto.KarloDTO;
import com.ssafy.a603.lingoland.writingGame.dto.KarloReturn;
import com.ssafy.a603.lingoland.writingGame.dto.KoGPTDTO;
import com.ssafy.a603.lingoland.writingGame.dto.KoGPTReturn;
import com.ssafy.a603.lingoland.writingGame.dto.WritingGameStartRequestDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WritingGameServiceImpl implements WritingGameService {
	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;
	private final ConcurrentHashMap<String, List<DrawingRequestDTO>> requestMap;
	private final RestClient restClient;
	private final String restApiKey;
	private final Translator translator;
	private final FairyTaleService fairyTaleService;

	public WritingGameServiceImpl(ObjectMapper objectMapper, RedisTemplate<String, Object> redisTemplate,
		@Value("${kakao.api.key}") String restApiKey, @Value("${deepL.api.key}") String deepLApiKey,
		FairyTaleService fairyTaleService) {
		this.objectMapper = objectMapper;
		this.redisTemplate = redisTemplate;
		this.requestMap = new ConcurrentHashMap<>();
		this.restClient = RestClient.builder()
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
			.build();
		this.restApiKey = restApiKey;
		this.translator = new Translator(deepLApiKey);
		this.fairyTaleService = fairyTaleService;
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
		if (requests.size() == sessionInfo.numPart()) {
			log.info("All members have submitted stories for session: {}", sessionId);
			List<DrawingRequestDTO> collected = new ArrayList<>(requests);
			requestMap.remove(sessionId);

			CompletableFuture<List<FairyTale>> future = CompletableFuture.runAsync(
					() -> processStoriesAsync(sessionId, collected, sessionInfo))
				.thenCompose(voidResult -> {
					if (requests.get(0).order() == sessionInfo.maxTurn()) {
						log.info("Final tasks after all stories are processed for session: {}", sessionId);
						return CompletableFuture.supplyAsync(() -> end(collected));
					} else {
						return CompletableFuture.completedFuture(Collections.emptyList());
					}
				});
			return future.join();
		}
		return Collections.emptyList();
	}

	@Async("sampleExecutor")
	private List<FairyTale> end(List<DrawingRequestDTO> requests) {
		log.info("Ending game session with requests: {}", requests);
		List<FairyTale> fairyTales = new ArrayList<>();
		List<CompletableFuture<FairyTale>> futures = new ArrayList<>();
		List<String> writers = new ArrayList<>();
		for (DrawingRequestDTO request : requests) {
			writers.add(request.key());
		}
		for (DrawingRequestDTO request : requests) {
			CompletableFuture<FairyTale> future = CompletableFuture.supplyAsync(() -> {
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
				AllamaSummaryResponseDTO titleWithSummary = makeTitleWithSummary(allStories.toString());
				log.info("make Title: {}", titleWithSummary.toString());
				String imgUrl = generateImage(titleWithSummary.toString());
				log.info("Generated image URL: {}", imgUrl);
				decodeBase64ToFile(imgUrl, "asdf.png");
				return fairyTaleService.createFairyTale(titleWithSummary.title(), imgUrl, titleWithSummary.summary(),
					stories, writers);
			});
			futures.add(future);
		}
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
		for (CompletableFuture<FairyTale> future : futures) {
			fairyTales.add(future.join());
		}
		return fairyTales;
	}

	@Async("sampleExecutor")
	public void processStoriesAsync(String sessionId, List<DrawingRequestDTO> requests,
		WritingGameStartRequestDTO sessionInfo) {
		log.info("Processing stories asynchronously for session: {}", sessionId);
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (DrawingRequestDTO request : requests) {
			CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
				String story = request.story();
				String translated = translate2English3(story);
				log.info("Translated story: {}", translated);
				String imgUrl = generateImage(translated);
				log.info("Generated image URL: {}", imgUrl);
				decodeBase64ToFile(imgUrl, "asdf.png");
				String redisStoryKey = "lingoland:fairyTale:" + request.key();
				FairyTale.Story node = FairyTale.Story.builder()
					.illustration(imgUrl)
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
			});
			futures.add(future);
		}
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
			.thenRun(() -> log.info("All story processing tasks initiated for session: {}", sessionId));
		log.info("Writing Game One Hop End");
	}

	private AllamaSummaryResponseDTO makeTitleWithSummary(String story) {
		log.info("Summary story using Allama3.1 : {}", story);
		String json = restClient.post()
			.uri("http://localhost:11434/api/generate")
			.body(new AllamaSummaryDTO(story))
			.retrieve()
			.body(String.class);
		try {
			JsonNode jsonNode = objectMapper.readTree(json);
			String response = jsonNode.get("response").asText();
			log.info("Summary result: {}", response);
			return objectMapper.readValue(response, new TypeReference<AllamaSummaryResponseDTO>() {
			});
		} catch (JsonProcessingException e) {
			log.error("Failed to process JSON response from translation API", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
	}

	private String translate2English3(String story) {
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
			return response;
		} catch (JsonProcessingException e) {
			log.error("Failed to process JSON response from translation API", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
	}

	private String translate2English2(String story) {
		log.info("Translating story using DeepL : {}", story);
		try {
			TextResult result = translator.translateText(story, "KO", "EN-US");
			return result.getText();
		} catch (Exception e) {
			log.error("Translation failed with DeepL", e);
			throw new InvalidInputException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}

	private String translate2English(String story) {
		log.info("Translating story using KoGPT : {}", story);
		KoGPTReturn koGPTReturn = restClient.post()
			.uri("https://api.kakaobrain.com/v1/inference/kogpt/generation")
			.header("Authorization", "KakaoAK " + restApiKey)
			.header("Content-Type", "application/json")
			.body(new KoGPTDTO(story))
			.retrieve()
			.body(KoGPTReturn.class);

		try {
			log.info("return : {}", objectMapper.writeValueAsString(koGPTReturn));
		} catch (JsonProcessingException e) {
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
		if (koGPTReturn != null && koGPTReturn.generations().length > 0) {
			return koGPTReturn.generations()[0].text();
		}
		log.error("Failed to generate image for translated text: {}", story);
		throw new InvalidInputException(ErrorCode.INVALID_INPUT_VALUE);
	}

	private String generateImage(String translated) {
		log.info("generate image using text {}", translated);
		KarloReturn karloReturn = null;
		karloReturn = restClient.post()
			.uri("https://api.kakaobrain.com/v2/inference/karlo/t2i")
			.header("Authorization", "KakaoAK " + restApiKey)
			.header("Content-Type", "application/json")
			.body(new KarloDTO(translated))
			.retrieve()
			.body(KarloReturn.class);

		if (karloReturn != null && karloReturn.images().length > 0) {
			log.info("image link : {}", karloReturn.images()[0].image());
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

	private void decodeBase64ToFile(String base64String, String filePath) {
		// Base64 디코더 초기화
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decodedBytes = decoder.decode(base64String);

		// 디코딩된 바이트 배열을 파일로 쓰기
		try (FileOutputStream fos = new FileOutputStream(filePath)) {
			fos.write(decodedBytes);
		} catch (IOException e) {
			throw new InvalidInputException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}
}
