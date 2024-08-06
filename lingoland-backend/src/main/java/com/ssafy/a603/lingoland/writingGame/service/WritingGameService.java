package com.ssafy.a603.lingoland.writingGame.service;

import com.ssafy.a603.lingoland.writingGame.dto.DrawingRequestDTO;
import com.ssafy.a603.lingoland.writingGame.dto.WritingGameStartRequestDTO;

public interface WritingGameService {
	public int[] start(String sessionId, WritingGameStartRequestDTO request);

	public void submitStory(String sessionId, DrawingRequestDTO dto);

	public void end();
}
