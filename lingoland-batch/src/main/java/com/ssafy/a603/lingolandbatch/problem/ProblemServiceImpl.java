package com.ssafy.a603.lingolandbatch.problem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a603.lingolandbatch.config.BatchConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
//@RequiredArgsConstructor
@Slf4j
public class ProblemServiceImpl implements ProblemService{

    private final ProblemRepository problemRepository;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private ProblemServiceImpl(ProblemRepository repository, WebClient.Builder webClient, ObjectMapper objectMapper){
        this.problemRepository = repository;
        this.webClient = webClient
                .build();
        this.objectMapper = objectMapper;
    }


    @Override
    public void makeProblem() {

        webClient.post()
                .headers(headers -> {
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue("{\n" +
                        "    \"model\": \"gpt-4o-mini\",\n" +
                        "    \"messages\": [\n" +
                        "      {\n" +
                        "        \"role\" : \"system\",\n" +
                        "        \"content\" : \"상황 : -사용자는 어휘력, 문법 능력을 향상시키고자 함. -사용자는 다양한 수준의 문제를 경험하고자 함. 입력값 : -사용자가 관심이 있는 특정 영역이나 주제 지시사항:-사용자의 관심 분야에 따라 관련 문제 생성-학습을 강화하기 위한 상호 작용형 퀴즈나 연습문제 제공-문제를 생성하고 다시 검토하기-문제는 선택지를 3개 제공한다.-정답은 반드시 1개이고 오답은 반드시 2개이다.-문제는 json 형식이다.-json내에 들어가는 문제 내용을 제외한 어떤 내용도 대답에 포함하지 마라.-문법 문제 1개, 어휘력 문제 2개, 문해력 문제 1개, 높임법 문제 1개를 내어라.가이드라인 :쉬운 어휘 사용출력 지시사항:json 형식{\\\"problem\\\": 문제제시,\\\"1\\\": 선택지 1,\\\"2\\\": 선택지 2,\\\"3\\\" : 선택지 3,\\\"answer\\\" : 답,“explanation”:”해설”}조건:-대한민국 초등학교 국어 교사가 사용한다.-시험문제를 만드는 상황이다.-문해력 저하에 대해 문제를 느끼고 있어 관련 문제를 제공한다.-난이도는 상,중,하 로 나누고 어려운 문제는 빈도를 낮추고 배치를 뒤로 한다.-문제를 생성할 때에 계획을 하고 문제를 만들기 위해 계획을 하나하나 실행하세요.-문제는 3개의 선택지로 제공한다.-정답은 무조건 1개이다.-문제를 생성할 때에는 모든 선택지를 검토한다.-문제를 생성할 때에는 출처가 있어야 한다.-문제 형식은 예시를 참고해야 한다.문제의 길이가 한 문장을 초과할 경우 문장마다 개행 문자를 삽입하라.-모든 문제는 'problems' 배열 내에 들어갸야 한다.\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }")
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> {
                                    log.info("{}", body);
                                    BatchConfig.setExecutionCntWhenError();
                                    return Mono.error(new WebClientResponseException(
                                            response.statusCode().value(),
                                            "HTTP Error: " + response.statusCode(),
                                            response.headers().asHttpHeaders(),
                                            null,
                                            null
                                    ));
                                })
                )
                .bodyToMono(String.class)
                .subscribe(
                        messageBody -> {
                            log.info("request success");
                            log.info("{}",messageBody);

                            try {
                                JsonNode jsonNode = objectMapper.readTree(messageBody);
                                JsonNode choiceNode = jsonNode.path("choices");
                                JsonNode messageNode = choiceNode.get(0).path("message");
                                String content = messageNode.path("content").asText();
                                String jsonString = content.replaceAll("```json\\n", "").replaceAll("\\n```", "");
                                JsonNode contentNode = objectMapper.readTree(jsonString);
                                JsonNode problemsNode = contentNode.path("problems");

                                JsonNode usageNode = jsonNode.path("usage");
                                int totalToken = usageNode.path("total_tokens").asInt();

                                log.info("total token spent {}", totalToken);
                                for(JsonNode problemNode : problemsNode){

                                    List<Problem.Detail.Choice> choices = new ArrayList<>();
                                    for(int j = 0; j < 3; j++) {
                                        choices.add(
                                                new Problem.Detail.Choice(
                                                        j + 1,
                                                        problemNode.path(String.valueOf(j + 1)).asText())
                                        );
                                    }

                                    Problem.Detail detail = new Problem.Detail(
                                            problemNode.path("problem").asText(),
                                            choices,
                                            problemNode.path("answer").asInt(),
                                            problemNode.path("explanation").asText()
                                    );

                                    Problem problem = Problem.builder()
                                            .correctAnswerCount(0L)
                                            .incorrectAnswerCount(0L)
                                            .deletedAt(null)
                                            .isDeleted(false)
                                            .inspector(null)
                                            .creator("chatGPT")
                                            .detail(detail)
                                            .build();

                                    problemRepository.save(problem);
                                    log.info("문제 저장: {}", problem);
                                }
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }

                            log.info("store success");
                        },
                        error -> {
                            log.info("request failed {}", error.getMessage());
                        }
                );
    }
}
