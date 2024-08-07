package com.ssafy.a603.lingoland.writingGame.dto;

public record AllamaStoryResponseDTO(String style, String target, String environment, String lighting, String color,
									 String atmosphere, String composition) {
	@Override
	public String toString() {
		return "{" +
			"style='" + style + '\'' +
			", target='" + target + '\'' +
			", environment='" + environment + '\'' +
			", lighting='" + lighting + '\'' +
			", color='" + color + '\'' +
			", atmosphere='" + atmosphere + '\'' +
			", composition='" + composition + '\'' +
			'}';
	}
}
