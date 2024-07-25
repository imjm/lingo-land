package com.ssafy.a603.lingoland.fairyTale.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a603.lingoland.fairyTale.dto.CreateFairyTaleRequestDTO;
import com.ssafy.a603.lingoland.fairyTale.service.FairyTaleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/fairy-tales")
public class FairyTaleController {
	private final FairyTaleService fairyTaleService;

	@PostMapping
	public ResponseEntity<?> createFairyTale(@RequestBody CreateFairyTaleRequestDTO dto) {
		fairyTaleService.createFairyTale(dto.title(), dto.cover(), dto.summary(), dto.content(), dto.writers());
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
