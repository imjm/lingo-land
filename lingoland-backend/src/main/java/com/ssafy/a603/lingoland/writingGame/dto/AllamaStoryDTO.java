package com.ssafy.a603.lingoland.writingGame.dto;

public record AllamaStoryDTO(String model, String prompt, boolean stream, String format) {
	public AllamaStoryDTO(String prompt) {
		this("llama3.1", keyword(prompt), false, "json");
	}

	private static String keyword(String prompt) {
		prompt = prompt.trim();
		return
			"You are a text analysis and extraction specialist. You are going to help preprocess sentences to generate images by extracting key descriptive elements and returning them in JSON format. Here is how you will analyze and extract the information:\n"
				+ "\n"
				+ "1. Analyze the sentence to identify key elements such as subjects, objects, and settings.\n"
				+ "2. Structure the extracted information into a JSON format with the specified fields.\n"
				+ "\n"
				+ "Analyze the following sentences: \"{{SENTENCES}}\"\n"
				+ "\n"
				+ "Return the results in the following JSON format, and ensure the descriptions are in English:\n"
				+ "{\n"
				+ "  \"style\": \"FairyTale\",\n"
				+ "  \"target\": \"target description\",\n"
				+ "  \"environment\": \"environment description\",\n"
				+ "  \"lighting\": \"lighting description\",\n"
				+ "  \"color\": \"color description\",\n"
				+ "  \"atmosphere\": \"mood description\",\n"
				+ "  \"composition\": \"composition description\"\n"
				+ "}\n"
				+ "\n"
				+ "Take a deep breath and let’s work this out in a step-by-step way to be sure we have the right answer.\n\n"
				+ prompt;
	}
}
