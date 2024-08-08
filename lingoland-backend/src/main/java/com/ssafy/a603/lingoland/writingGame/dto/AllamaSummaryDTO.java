package com.ssafy.a603.lingoland.writingGame.dto;

public record AllamaSummaryDTO(String model, String prompt, boolean stream, String format) {
	public AllamaSummaryDTO(String prompt) {
		this("llama3.1", preprocess(prompt), false, "json");
	}

	private static String preprocess(String prompt) {
		return
			"You are a professional fairy tale editor. Your task is to read the provided fairy tale and create an appropriate title and prologue for it. Follow these steps to ensure a high-quality output:\n"
				+ "\n"
				+ "Read the entire fairy tale carefully.\n"
				+ "Identify the main theme and key elements of the story.\n"
				+ "Create a compelling and suitable title that captures the essence of the fairy tale.\n"
				+ "Write a captivating prologue that introduces the story and sets the tone, without giving away too much detail.\n"
				+ "Analyze the following fairy tale: \"{{FAIRY_TALE}}\"\n"
				+ "\n"
				+ "Return the results in the following JSON format:\n"
				+ "{\n"
				+ "\"title\": \"Title of the fairy tale\",\n"
				+ "\"summary\": \"Prologue of the fairy tale\"\n"
				+ "}\n"
				+ "\n"
				+ "Take a deep breath and let's work this out in a step-by-step way to be sure we have the right answer.\n\n"
				+ prompt.trim();
	}
}
