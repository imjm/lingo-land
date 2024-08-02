package com.ssafy.a603.lingoland.writingGame.dto;

public record KarloDTO(String version, String prompt, String negative_prompt, Integer height, Integer width) {
	public KarloDTO(String prompt) {
		this("v2.1", prompt,
			"nsfw, bad anatomy, out of frame, distortion,"
				+ " text, bent, uneven shape, blurry, grainy, low resolution, worst quality,"
				+ " fuzzy, lowres, low quality, normal quality, signature, watermark, deformity,"
				+ " deformed body, face distortion",
			1024, 1024);
	}
}
