package com.ssafy.a603.lingoland.writingGame.service;

import com.ssafy.a603.lingoland.writingGame.dto.DrawingRequestDTO;

public interface WritingGameService {
	public void start();

	public void submitStory(String sessionId, DrawingRequestDTO dto);

	public void end();
}
