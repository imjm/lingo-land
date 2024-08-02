package com.ssafy.a603.lingoland.writingGame;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a603.lingoland.writingGame.dto.DrawingRequestDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/writing-game")
public class WritingGameController {
	private final WritingGameService writingGameService;

	@PostMapping("/request/{sessionId}")
	public ResponseEntity<?> request(@PathVariable("sessionId") String sessionId, @RequestBody DrawingRequestDTO dto) {
		writingGameService.submitStory(sessionId, dto);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
