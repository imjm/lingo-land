package com.ssafy.a603.lingoland.writingGame.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

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
import com.ssafy.a603.lingoland.global.error.entity.ErrorCode;
import com.ssafy.a603.lingoland.global.error.exception.BaseException;
import com.ssafy.a603.lingoland.global.error.exception.InvalidInputException;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.room.service.RoomService;
import com.ssafy.a603.lingoland.util.ImgUtils;
import com.ssafy.a603.lingoland.writingGame.dto.DrawingRequestDTO;
import com.ssafy.a603.lingoland.writingGame.dto.KarloDTO;
import com.ssafy.a603.lingoland.writingGame.dto.KarloReturn;
import com.ssafy.a603.lingoland.writingGame.dto.OllamaStoryDTO;
import com.ssafy.a603.lingoland.writingGame.dto.OllamaStoryResponseDTO;
import com.ssafy.a603.lingoland.writingGame.dto.OllamaSummaryDTO;
import com.ssafy.a603.lingoland.writingGame.dto.OllamaSummaryResponseDTO;
import com.ssafy.a603.lingoland.writingGame.dto.SessionInfo;
import com.ssafy.a603.lingoland.writingGame.dto.SubmitStoryResponseDTO;
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
	private final RestClient restClient;
	private final ImgUtils imgUtils;
	private final RoomService roomService;

	@Qualifier("myExecutor")
	private final ExecutorService executor;

	@Value("${kakao.api.key}")
	private String imageApiKey;

	@Value("${ollama.api-url}")
	private String ollamaUrl;

	@Override
	public void start(String sessionId, WritingGameStartRequestDTO request) {
		log.info("Starting game for session: {}", sessionId);
		String redisKey = makeRedisRoomKey(sessionId);
		SessionInfo sessionInfo = new SessionInfo(request.maxTurn());
		serializeToRedis(redisKey, sessionInfo);
	}

	@Override
	public void setTitle(String sessionId, CustomUserDetails customUserDetails, String title) {
		// 룸에 누구 있는지 정보 세팅
		String redisRoomKey = makeRedisRoomKey(sessionId);
		SessionInfo sessionInfo = deserializeFromRedis(redisRoomKey, SessionInfo.class);
		sessionInfo.addParticipant(customUserDetails.getUsername());
		serializeToRedis(redisRoomKey, sessionInfo);

		//각 유저에 대한 정보 세팅
		String redisAliveKey = makeRedisMemberAliveKey(sessionId, customUserDetails.getUsername());
		serializeToRedis(redisAliveKey, true);

		String redisSubmitKey = makeRedisMemberSubmitKey(sessionId, customUserDetails.getUsername());
		serializeToRedis(redisSubmitKey, false);

		roomService.create(sessionId, customUserDetails, title);
	}

	@Override
	public SubmitStoryResponseDTO submitStory(String sessionId, DrawingRequestDTO request,
		CustomUserDetails customUserDetails) {
		log.info("Submitting story for session: {}", sessionId);
		String redisSubmitKey = makeRedisMemberSubmitKey(sessionId, customUserDetails.getUsername());
		serializeToRedis(redisSubmitKey, true);

		processSingleStory(sessionId, request, customUserDetails);

		String redisRoomKey = makeRedisRoomKey(sessionId);
		SessionInfo sessionInfo = deserializeFromRedis(redisRoomKey, SessionInfo.class);
		List<FairyTale> fairyTales;
		if (sessionInfo.getMaxTurn() == request.order()) {
			//마지막 턴의 경우 list 리턴 + true false return
			//일단 표지 그려라 요청
			endProcess(sessionId, request, customUserDetails);
			// end -> list를 가져온다.
			fairyTales = roomService.findFairyTalesInSession(sessionId);
			// 기존의 게임 룸에 대한 redis, room entity 제거
			deleteKeysByPattern(makeRedisRoomKey(sessionId));
			roomService.endRoom(sessionId);
		} else {
			fairyTales = null;
		}

		if (isAllEnd(sessionId, sessionInfo)) {
			for (String participant : sessionInfo.getParticipants()) {
				redisSubmitKey = makeRedisMemberSubmitKey(sessionId, participant);
				serializeToRedis(redisSubmitKey, false);
			}
			return SubmitStoryResponseDTO.builder()
				.fairyTales(fairyTales)
				.goNext(true)
				.build();
		}
		return SubmitStoryResponseDTO.builder()
			.fairyTales(fairyTales)
			.goNext(false)
			.build();
	}

	@Override
	public Boolean exit(String sessionId, String exitLoginId, Integer order) {
		String redisAliveKey = makeRedisMemberAliveKey(sessionId, exitLoginId);
		serializeToRedis(redisAliveKey, false);
		String redisRoomKey = makeRedisRoomKey(sessionId);
		SessionInfo sessionInfo = deserializeFromRedis(redisRoomKey, SessionInfo.class);
		if (order != sessionInfo.getMaxTurn()) {
			String redisSubmitKey = makeRedisMemberSubmitKey(sessionId, exitLoginId);
			Boolean isSubmit = deserializeFromRedis(redisSubmitKey, Boolean.class);
			if (!isSubmit) {
				roomService.fairytaleInComplete(sessionId, exitLoginId);
			}
		}
		return isAllEnd(sessionId, sessionInfo);
	}

	//번역 -> 그림 -> 저장
	@Async("asyncExecutor")
	protected void processSingleStory(String sessionId, DrawingRequestDTO request,
		CustomUserDetails customUserDetails) {
		log.info("Processing single story: {} for session: {}", request.key(), sessionId);
		String story = request.story();
		String imgUrl = handleStoryTranslation(story);
		String savedImgUrl = null;
		if (imgUrl.equals(imgUtils.getDefaultImage()))
			savedImgUrl = imgUtils.getImagePathWithDefaultImage(FAIRY_TALE_IMAGE_PATH);
		else
			savedImgUrl = imgUtils.saveBase64Image(imgUrl, FAIRY_TALE_IMAGE_PATH);
		log.debug("Story image saved at: {}", savedImgUrl);
		saveStory(request.key(), request, customUserDetails, savedImgUrl);
	}

	@Async("asyncExecutor")
	protected void endProcess(String sessionId, DrawingRequestDTO request, CustomUserDetails customUserDetails) {
		FairyTale curFairyTale = roomService.findFairyTale(sessionId, customUserDetails.getUsername());
		String stories = "";
		for (FairyTale.Story story : curFairyTale.getContent()) {
			stories += story.getStory() + "\n";
		}

		String imgUrl;
		OllamaSummaryResponseDTO imagePrompt = null;
		try {
			imagePrompt = makeTitleWithSummary(stories);
		} catch (Exception e) {
			log.warn("Skipping image generation due to title creation failure.");
			imgUrl = imgUtils.getDefaultImage();
		}

		if (imagePrompt == null || imagePrompt.content() == null) {
			imgUrl = imgUtils.getDefaultImage();
		} else {
			imgUrl = generateImageWithFallback(imagePrompt.content().toString());
		}

		String savedImgUrl;
		if (imgUrl.equals(imgUtils.getDefaultImage()))
			savedImgUrl = imgUtils.getImagePathWithDefaultImage(FAIRY_TALE_IMAGE_PATH);
		else
			savedImgUrl = imgUtils.saveBase64Image(imgUrl, FAIRY_TALE_IMAGE_PATH);
		roomService.fairytaleComplete(sessionId, request.key(), savedImgUrl, imagePrompt.summary());
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

	private void saveStory(String sessionId, DrawingRequestDTO request, CustomUserDetails customUserDetails,
		String imgUrl) {
		roomService.fairytaleStoryAdd(sessionId, request.key(), customUserDetails,
			new FairyTale.Story(imgUrl, request.key()));
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
			TypeReference<T> valueTypeRef = new TypeReference<T>() {
			};
			return objectMapper.readValue(json, valueTypeRef);
		} catch (JsonProcessingException e) {
			log.error("Failed to deserialize object from Redis", e);
			throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
		}
	}

	private boolean isAllEnd(String sessionId, SessionInfo sessionInfo) {
		String redisAliveKey;
		String redisSubmitKey;
		Boolean isAlive;
		Boolean isSubmit;
		int count = 0;
		for (String participant : sessionInfo.getParticipants()) {
			redisAliveKey = makeRedisMemberAliveKey(sessionId, participant);
			redisSubmitKey = makeRedisMemberSubmitKey(sessionId, participant);
			isAlive = deserializeFromRedis(redisAliveKey, Boolean.class);
			if (!isAlive) {
				count++;
				continue;
			}
			isSubmit = deserializeFromRedis(redisSubmitKey, Boolean.class);
			if (isSubmit) {
				count++;
			}
		}
		return count == sessionInfo.getParticipants().size();
	}

	private String makeRedisRoomKey(String sessionId) {
		return "lingoland:fairyTale:session:" + sessionId;
	}

	private String makeRedisMemberAliveKey(String sessionId, String loginId) {
		return "lingoland:fairyTale:session:" + sessionId + ":member:" + loginId + ":alive";
	}

	private String makeRedisMemberSubmitKey(String sessionId, String loginId) {
		return "lingoland:fairyTale:session:" + sessionId + ":member:" + loginId + ":submit";
	}

	private void deleteKeysByPattern(String pattern) {
		Set<String> keySet = redisTemplate.keys(pattern);
		redisTemplate.delete(keySet);
	}

	// @Async("asyncExecutor")
	// protected CompletableFuture<Void> processStoriesAsync(String sessionId, List<DrawingRequestDTO> requests,
	// 	WritingGameStartRequestDTO sessionInfo) {
	// 	log.info("Processing stories asynchronously for session: {}", sessionId);
	// 	List<CompletableFuture<Void>> futures = new ArrayList<>();
	// 	for (DrawingRequestDTO request : requests) {
	// 		log.debug("Processing single story for session: {}, order: {}", sessionId, request.order());
	// 		futures.add(CompletableFuture.runAsync(() -> processSingleStory(sessionId, request), executor));
	// 	}
	// 	return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
	// 		.thenRun(() -> log.info("All story processing tasks completed for session: {}", sessionId));
	// }
	// private void saveStoryToRedis(String key, DrawingRequestDTO request, String savedImgUrl) {
	// 	log.info("Saving story to Redis with key: {}", key);
	// 	String redisStoryKey = "lingoland:fairyTale:" + key;
	// 	FairyTale.Story node = FairyTale.Story.builder()
	// 		.illustration(savedImgUrl)
	// 		.story(request.story())
	// 		.build();
	//
	// 	try {
	// 		if (request.order() == 1) {
	// 			List<FairyTale.Story> stories = new ArrayList<>();
	// 			stories.add(node);
	// 			serializeToRedis(redisStoryKey, stories);
	// 			log.debug("Serialized new story to Redis for key: {}", redisStoryKey);
	// 			return;
	// 		}
	// 		List<FairyTale.Story> existingStories = getExistingStories(redisStoryKey);
	// 		existingStories.add(node);
	// 		serializeToRedis(redisStoryKey, existingStories);
	// 		log.debug("Serialized updated story to Redis for key: {}", redisStoryKey);
	// 	} catch (JsonProcessingException e) {
	// 		log.error("Error processing JSON for Redis serialization", e);
	// 		throw new BaseException(ErrorCode.JSON_PROCESSING_FAILED);
	// 	}
	// }
	//
	// private List<FairyTale.Story> getExistingStories(String redisStoryKey) throws JsonProcessingException {
	// 	String existingJson = (String)redisTemplate.opsForValue().get(redisStoryKey);
	// 	return existingJson != null ? objectMapper.readValue(existingJson, new TypeReference<List<FairyTale.Story>>() {
	// 	}) : new ArrayList<>();
	// }
	// @Async("asyncExecutor")
	// protected List<FairyTale> end(List<DrawingRequestDTO> requests) {
	// 	log.info("Ending game session with {} requests", requests.size());
	//
	// 	List<String> writers = requests.stream()
	// 		.map(DrawingRequestDTO::key)
	// 		.collect(Collectors.toList());
	// 	log.debug("Writers involved in this session: {}", writers);
	//
	// 	List<CompletableFuture<FairyTale>> futures = requests.stream()
	// 		.map(request -> CompletableFuture.supplyAsync(() -> {
	// 				log.info("Processing end of session for request: {}", request.key());
	// 				return endProcess(request, writers);
	// 			}, executor)
	// 			.exceptionally(ex -> {
	// 				log.error("Error processing request: {}", request.key(), ex);
	// 				throw new InvalidInputException(ErrorCode.INTERNAL_SERVER_ERROR);
	// 			}))
	// 		.collect(Collectors.toList());
	//
	// 	log.info("Waiting for all end-process tasks to complete");
	// 	CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
	//
	// 	List<FairyTale> fairyTales = futures.stream()
	// 		.map(CompletableFuture::join)
	// 		.collect(Collectors.toList());
	// 	log.info("Completed end-process tasks for all requests");
	//
	// 	return fairyTales;
	// }
}

