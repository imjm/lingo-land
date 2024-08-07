package com.ssafy.a603.lingoland.writingGame.dto;

public record AllamaSummaryResponseDTO(String title, String summary) {
	@Override
	public String toString() {
		return "{" +
			"title='" + title + '\'' +
			", summary='" + summary + '\'' +
			'}';
	}
}
