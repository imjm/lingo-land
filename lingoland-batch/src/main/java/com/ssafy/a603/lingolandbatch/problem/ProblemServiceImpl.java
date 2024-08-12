package com.ssafy.a603.lingolandbatch.problem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
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
                .bodyValue("{\n" +
                        "    \"model\": \"gpt-4o-mini\",\n" +
                        "    \"messages\": [\n" +
                        "      {\n" +
                        "        \"role\" : \"system\",\n" +
                        "        \"content\" : \"상황 : -사용자는 어휘력, 문법 능력을 향상시키고자 함. -사용자는 다양한 수준의 문제를 경험하고자 함. 입력값 : -사용자가 관심이 있는 특정 영역이나 주제 지시사항:-사용자의 관심 분야에 따라 관련 문제 생성-학습을 강화하기 위한 상호 작용형 퀴즈나 연습문제 제공-문제를 생성하고 다시 검토하기-문제는 선택지를 3개 제공한다.-정답은 반드시 1개이고 오답은 반드시 2개이다.-문제는 json 형식이다.-json내에 들어가는 문제 내용을 제외한 어떤 내용도 대답에 포함하지 마라.-문법 문제 3개, 어휘력 문제 3개, 문해력 문제 3개, 높임법 문제 1개를 내어라.가이드라인 :쉬운 어휘 사용출력 지시사항:json 형식{\\\"problem\\\": 문제제시,\\\"1\\\": 선택지 1,\\\"2\\\": 선택지 2,\\\"3\\\" : 선택지 3,\\\"4\\\": 선택지 4,\\\"answer\\\" : 답,“explanation”:”해설”}조건:-대한민국 초등학교 국어 교사가 사용한다.-시험문제를 만드는 상황이다.-문해력 저하에 대해 문제를 느끼고 있어 관련 문제를 제공한다.-난이도는 상,중,하 로 나누고 어려운 문제는 빈도를 낮추고 배치를 뒤로 한다.-문제를 생성할 때에 계획을 하고 문제를 만들기 위해 계획을 하나하나 실행하세요.-문제는 4개의 선택지로 제공한다.-정답은 무조건 1개이다.-문제를 생성할 때에는 모든 선택지를 검토한다.-문제를 생성할 때에는 출처가 있어야 한다.-문제 형식은 예시를 참고해야 한다.문제의 길이가 한 문장을 초과할 경우 문장마다 개행 문자를 삽입하라.\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }")
                .retrieve()
//                .onStatus(HttpStatus::isError, clientResponse -> {
//                    return Mono.error(new WebClientException(
//                            "token required"
//                    ) {
//                    });
//        })
                .bodyToMono(String.class)
                .subscribe(
                        messageBody -> {
                            log.info("request success");
                            System.out.println(messageBody);

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

                                System.out.println("total token " + totalToken);
                                for(JsonNode problemNode : problemsNode){

                                    System.out.println("********" + "********");

                                    List<ChoiceDTO> choiceDTOs = new ArrayList<>();
                                    JsonNode detailNode = problemNode.path("detail");
                                    for(int j = 0; j < 3; j++) {
                                        choiceDTOs.add(
                                                ChoiceDTO.builder()
                                                .num(j+1)
                                                .text(detailNode.path(String.valueOf(j+1)).asText())
                                                .build());
                                    }
                                    DetailDTO detailDTO = DetailDTO.builder()
                                            .answer(problemNode.path("answer").asInt())
                                            .explanation(problemNode.path("explanation").asText())
                                            .choiceDTOS(choiceDTOs)
                                                    .build();

                                    ProblemDTO problemDTO = ProblemDTO.builder()
                                            .detailDTO(detailDTO)
                                            .correctAnswerCount(0)
                                            .isDeleted(false)
                                            .inspector(null)
                                            .creator("chatGPT")
                                            .deletedAt(null)
                                            .incorrectAnswerCount(0)
                                            .build();

                                    List<Problem.Detail.Choice> choices = new ArrayList<>();
                                    for(int j = 0; j < 3; j++){
                                        choices.add(Problem.Detail.Choice.builder()
                                                .num(choiceDTOs.get(j).getNum())
                                                .text(choiceDTOs.get(j).getText())
                                                .build());
                                    }

                                    Problem.Detail detail = Problem.Detail.builder()
                                            .answer(detailDTO.getAnswer())
                                            .choices(choices)
                                            .explanation(detailDTO.getExplanation())
                                            .problem(detailDTO.getProblem())
                                            .build();

                                    System.out.println(problemDTO);
                                    System.out.println(detailDTO);
                                    for(int j = 0; j < 3; j++){
                                        System.out.println(choiceDTOs.get(j));
                                    }

                                    problemRepository.save(Problem.builder()
                                            .correctAnswerCount(problemDTO.getCorrectAnswerCount())
                                            .isDeleted(problemDTO.isDeleted())
                                            .inspector(problemDTO.getInspector())
                                            .creator(problemDTO.getCreator())
                                            .deletedAt(problemDTO.getDeletedAt())
                                            .incorrectAnswerCount(problemDTO.getIncorrectAnswerCount())
                                            .detail(detail)
                                            .build());
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
