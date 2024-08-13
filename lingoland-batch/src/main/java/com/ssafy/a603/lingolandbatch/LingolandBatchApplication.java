package com.ssafy.a603.lingolandbatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableBatchProcessing
@Slf4j
public class LingolandBatchApplication implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("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");
        String requestBody = "{\n" +
                "    \"model\": \"gpt-4o-mini\",\n" +
                "    \"messages\": [\n" +
                "      {\n" +
                "        \"role\": \"system\",\n" +
                "        \"content\": \"You are a helpful assistant. 한국말로 답하라.\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"role\": \"user\",\n" +
                "        \"content\": \"Hello!\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        HttpHeaders responseHeaders = response.getHeaders();
        System.out.println("header");
        responseHeaders.forEach((key, value) -> System.out.println((key + " : " + value)));

        String responseBody = response.getBody();
        System.out.println("Body\n" + responseBody);

        System.out.println("__________________________");

        log.info("app start");
        SpringApplication.run(LingolandBatchApplication.class, args);
        log.info("app end");
    }

    @Override
    public void run(String... args) throws Exception {
        jobLauncher.run(job, new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters());
    }
}
