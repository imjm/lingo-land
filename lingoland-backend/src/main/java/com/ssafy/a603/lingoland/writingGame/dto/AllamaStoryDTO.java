package com.ssafy.a603.lingoland.writingGame.dto;

public record AllamaStoryDTO(String model, String prompt, boolean stream, String format) {
	public AllamaStoryDTO(String prompt) {
		this("llama3.1", keyword(prompt), false, "json");
	}

	private static String keyword(String prompt) {
		return
			"You are a text analysis and extraction specialist. You are going to help preprocess sentences to generate images by extracting key descriptive elements from the most important sentence and returning them in JSON format. Here is how you will analyze and extract the information:\n"
				+ "\n"
				+ "1. Analyze the given sentences and identify the most important sentence based on descriptive content.\n"
				+ "2. From the selected sentence, extract key elements such as subjects, objects, and settings.\n"
				+ "3. Structure the extracted information into a JSON format with the specified fields, ensuring that nested structures are flattened.\n"
				+ "\n"
				+ "Analyze the following sentences: \"{{SENTENCES}}\"\n"
				+ "\n"
				+ "Return the results in the following JSON format, ensuring that the medium is always set to 'fairytale illustration' and the descriptions are in English:\n"
				+ "{\n"
				+ "  \"medium\": \"fairytale illustration\",\n"
				+ "  \"character\": \"character description\",\n"
				+ "  \"environment\": \"environment description\",\n"
				+ "  \"lighting\": \"lighting description\",\n"
				+ "  \"color\": \"color description\",\n"
				+ "  \"mood\": \"mood description\",\n"
				+ "  \"composition\": \"composition description\"\n"
				+ "}\n"
				+ "**Important**: Make sure to strictly follow the JSON format. Do not include any additional text or explanations outside of the JSON structure.\n"
				+ "\n"
				+ "Take a deep breath and let’s work this out in a step-by-step way to be sure we have the right answer.\n\n"
				+ prompt.trim();
	}
}
