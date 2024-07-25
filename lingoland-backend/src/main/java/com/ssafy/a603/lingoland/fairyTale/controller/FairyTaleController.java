package com.ssafy.a603.lingoland.fairyTale.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a603.lingoland.fairyTale.dto.CreateFairyTaleRequestDTO;
import com.ssafy.a603.lingoland.fairyTale.dto.FairyTaleListResponseDTO;
import com.ssafy.a603.lingoland.fairyTale.service.FairyTaleService;
import com.ssafy.a603.lingoland.member.security.CurrentUser;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;

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

	@GetMapping("/members")
	public ResponseEntity<?> findListByLoginId(@CurrentUser CustomUserDetails customUserDetails) {
		List<FairyTaleListResponseDTO> fairyTales = fairyTaleService.findFairyTaleListByLoginId(customUserDetails);
		return ResponseEntity.status(HttpStatus.OK).body(fairyTales);
	}

	@GetMapping("/{fairyTaleId}")
	public ResponseEntity<?> findFairyTaleById(@PathVariable("fairyTaleId") Integer fairyTaleId) {
		return null;
	}

	@PutMapping("/{fairyTaleId}")
	public ResponseEntity<?> invisible(@PathVariable("fairyTaleId") Integer fairyTaleId,
		@CurrentUser CustomUserDetails customUserDetails) {
		return null;
	}
}
