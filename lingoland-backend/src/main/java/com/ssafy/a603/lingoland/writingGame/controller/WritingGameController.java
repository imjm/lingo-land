package com.ssafy.a603.lingoland.writingGame.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a603.lingoland.writingGame.dto.DrawingRequestDTO;
import com.ssafy.a603.lingoland.writingGame.dto.WritingGameStartRequestDTO;
import com.ssafy.a603.lingoland.writingGame.service.WritingGameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/writing-game")
public class WritingGameController {
	private final WritingGameService writingGameService;

	@PostMapping("/start/{sessionId}")
	public ResponseEntity<?> start(@PathVariable("sessionId") String sessionId,
		@RequestBody WritingGameStartRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(writingGameService.start(sessionId, dto));
	}

	@PostMapping("/request/{sessionId}")
	public ResponseEntity<?> request(@PathVariable("sessionId") String sessionId, @RequestBody DrawingRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.OK).body(writingGameService.submitStory(sessionId, dto));
	}

	@PostMapping("/test/{sessionId}")
	public ResponseEntity<?> test(@PathVariable("sessionId") String sessionId, @RequestBody DrawingRequestDTO dto) {
		writingGameService.test(sessionId, dto);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
