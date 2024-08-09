package com.ssafy.a603.lingoland.writingGame.dto;

public record AllamaSummaryDTO(String model, String prompt, boolean stream, String format) {
	public AllamaSummaryDTO(String prompt) {
		this("llama3.1", preprocess(prompt), false, "json");
	}

	private static String preprocess(String prompt) {
		return
			"You are a professional fairy tale editor. Your task is to read the provided fairy tale and create an appropriate title and prologue for it. Follow these steps to ensure a high-quality output:\n"
				+ "\n"
				+ "1. Read the entire fairy tale carefully.\n"
				+ "2. Identify the main theme and key elements of the story.\n"
				+ "3. Create a compelling and suitable title that captures the essence of the fairy tale.\n"
				+ "4. Write a captivating prologue that introduces the story and sets the tone, without giving away too much detail.\n"
				+ "5. Analyze the given sentences and identify the most important sentence based on descriptive content.\n"
				+ "6. From the selected sentence, extract key elements such as subjects, objects, and settings.\n"
				+ "7. Structure the extracted information into a JSON format with the specified fields, ensuring that nested structures are flattened.\n"
				+ "8. Analyze the following fairy tale: \"{{FAIRY_TALE}}\"\n"
				+ "\n"
				+ "Return the results in the following JSON format:\n"
				+ "{\n"
				+ "\"title\": \"Title of the fairy tale\",\n"
				+ "\"summary\": \"Prologue of the fairy tale\"\n"
				+ "\"content\": {\n"
				+ "  \"medium\": \"fairytale illustration\",\n"
				+ "  \"character\": \"character description\",\n"
				+ "  \"environment\": \"environment description\",\n"
				+ "  \"lighting\": \"lighting description\",\n"
				+ "  \"color\": \"color description\",\n"
				+ "  \"mood\": \"mood description\",\n"
				+ "  \"composition\": \"composition description\"\n"
				+ "}\n"
				+ "}\n"
				+ "**Important**: Make sure to strictly follow the JSON format. Do not include any additional text or explanations outside of the JSON structure.\n"
				+ "\n"
				+ "Take a deep breath and let's work this out in a step-by-step way to be sure we have the right answer.\n\n"
				+ prompt.trim();
	}
}
