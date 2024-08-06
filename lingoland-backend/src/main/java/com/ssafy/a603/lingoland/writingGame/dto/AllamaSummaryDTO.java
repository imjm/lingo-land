package com.ssafy.a603.lingoland.writingGame.dto;

public record AllamaSummaryDTO(String model, String prompt, boolean stream, String format) {
	public AllamaSummaryDTO(String prompt) {
		this("llama3.1", preprocess(prompt), false, "json");
	}

	private static String preprocess(String prompt) {
		return
			"You are a creative storytelling expert specialized in children's fairy tales. You will read the provided story and generate a suitable title and a captivating prologue (summary). The story is provided in plain text and your response should be in the following JSON format:\n"
				+ "\n"
				+ "json format\n"
				+ "{\n"
				+ "    \"title\" : \"{{title}}\",\n"
				+ "    \"summary\" : \"{{prologue}}\"\n"
				+ "}\n"
				+ "Here is the detailed approach you will follow:\n"
				+ "\n"
				+ "Analyze the Story: Carefully read and understand the main plot, characters, and theme of the provided fairy tale.\n"
				+ "Review the Provided Prologue: Read the initial prologue and refine it if necessary to ensure it aligns well with the story.\n"
				+ "Generate the Title: Create a concise and appealing title in Korean that captures the essence of the story.\n"
				+ "Write or Refine the Prologue: Craft or refine a brief, engaging summary of the story in Korean that serves as a prologue, enticing readers to read the full story. The prologue should not exceed 200 characters.\n"
				+ "Format the Response: Ensure the response is in the specified JSON format with the \"title\" and \"summary\" fields filled appropriately in Korean.\n"
				+ "Take a deep breath and let's work this out in a step by step way to be sure we have the right answer.\n\n"
				+ prompt.trim();
	}
}
