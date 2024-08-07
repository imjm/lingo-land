package com.ssafy.a603.lingoland.writingGame.service;

import java.util.List;

import com.ssafy.a603.lingoland.fairyTale.entity.FairyTale;
import com.ssafy.a603.lingoland.writingGame.dto.DrawingRequestDTO;
import com.ssafy.a603.lingoland.writingGame.dto.WritingGameStartRequestDTO;

public interface WritingGameService {
	int[] start(String sessionId, WritingGameStartRequestDTO request);

	List<FairyTale> submitStory(String sessionId, DrawingRequestDTO dto);

	void test(String sessionId, DrawingRequestDTO dto);
}
