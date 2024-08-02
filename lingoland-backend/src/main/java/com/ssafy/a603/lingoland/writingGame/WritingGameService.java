package com.ssafy.a603.lingoland.writingGame;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a603.lingoland.global.error.entity.ErrorCode;
import com.ssafy.a603.lingoland.global.error.exception.InvalidInputException;
import com.ssafy.a603.lingoland.writingGame.dto.DrawingRequestDTO;
import com.ssafy.a603.lingoland.writingGame.dto.KarloDTO;
import com.ssafy.a603.lingoland.writingGame.dto.KarloReturn;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class WritingGameService {
	private final ObjectMapper objectMapper;
	private final RedisTemplate<String, Object> redisTemplate;
	private final ConcurrentHashMap<String, List<DrawingRequestDTO>> requestMap = new ConcurrentHashMap<>();

	@Value("${kakao.api.key}")
	private String restApiKey;

	public void submitStory(String sessionId, DrawingRequestDTO dto) {
		log.info("Submitting story for session: {}", sessionId);
		requestMap.putIfAbsent(sessionId, new ArrayList<>());
		List<DrawingRequestDTO> requests = requestMap.get(sessionId);

		requests.add(dto);
		if (requests.size() == dto.numPart()) {
			log.info("All Member Submit Story!!!");
			List<DrawingRequestDTO> collected = new ArrayList<>(requests);
			requestMap.remove(sessionId);
			processStoriesAsync(collected);
		}
	}

	@Async("sampleExecutor")
	public void processStoriesAsync(List<DrawingRequestDTO> requests) {
		log.info("Processing stories asynchronously");
		for (DrawingRequestDTO request : requests) {
			CompletableFuture.runAsync(() -> {
				String story = request.story();
				String translated = translate2English(story);
				log.info("Translated story: {}", translated);
				String imgUrl = generateImage(translated);
				log.info("Generated image URL: {}", imgUrl);
			});
		}
		log.info("Writing Game One Hop End");
	}

	private String translate2English(String story) {
		// TODO : 카카오 koGPT에 올라온 글들 중에서 핵심 키워드 목록을 영어로 정리해 뽑아달라고 하자.
		return "apple, banana, monkey";
	}

	private String generateImage(String translated) {
		log.info("kakao rest Api key {}", restApiKey);
		RestClient restClient = RestClient.builder()
			.defaultStatusHandler(
				HttpStatusCode::is4xxClientError,
				(request, response) -> {
					log.error("Client Error Code={}", response.getStatusCode());
					log.error("Client Error Message={}", new String(response.getBody().readAllBytes()));
				})
			.defaultStatusHandler(
				HttpStatusCode::is5xxServerError,
				(request, response) -> {
					log.error("Server Error Code={}", response.getStatusCode());
					log.error("Server Error Message={}", new String(response.getBody().readAllBytes()));
				})
			.build();
		try {
			log.info("karlo request to JSON : {}", objectMapper.writeValueAsString(new KarloDTO(translated)));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
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
}
