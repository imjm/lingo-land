package com.ssafy.a603.lingoland.writingGame.dto;

public record DrawingRequestDTO(String loginId, String story, int numPart, boolean isFirst) {
}
