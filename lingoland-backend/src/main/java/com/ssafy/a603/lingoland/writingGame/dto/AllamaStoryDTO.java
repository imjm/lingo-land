package com.ssafy.a603.lingoland.writingGame.dto;

public record AllamaStoryDTO(String model, String prompt, boolean stream) {
	public AllamaStoryDTO(String prompt) {
		this("llama3.1", keyword(prompt), false);
	}

	private static String keyword(String prompt) {
		prompt = prompt.trim();
		return
			"You are an AI assistant specialized in keyword extraction. You will read the provided Korean sentences and extract relevant keywords in English. The goal is to use these keywords to instruct a generative AI to create an image. Provide the keywords in a comma-separated list in English without any additional text or explanations.\n"
				+ "\n"
				+ "Here is the detailed approach you will follow:\n"
				+ "1. Analyze the Korean Sentences: Carefully read and understand the provided Korean sentences.\n"
				+ "2. Extract Keywords: Identify and list the most relevant keywords that capture the essence of the sentences in English.\n"
				+ "3. Format the Response: Ensure the response is a comma-separated list of keywords in English without any additional text or explanations.\n"
				+ "\n"
				+ "Take a deep breath and let's work this out in a step by step way to be sure we have the right answer.\n\n"
				+ prompt;
	}
}
