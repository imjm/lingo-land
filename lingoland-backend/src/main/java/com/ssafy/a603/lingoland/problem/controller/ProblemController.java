package com.ssafy.a603.lingoland.problem.controller;

import com.ssafy.a603.lingoland.member.security.CurrentUser;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.problem.dto.CreateGameResultsDto;
import com.ssafy.a603.lingoland.problem.dto.GetWrongProblemsDto;
import com.ssafy.a603.lingoland.problem.service.ProblemService;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping("/save-results")
    public ResponseEntity<?> createGameResults(@Valid @RequestBody CreateGameResultsDto createGameResultsDto,
                                               @CurrentUser CustomUserDetails customUserDetails) {
        problemService.createGameResults(createGameResultsDto, customUserDetails);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // should be revised to find random problem of specific kind.

    @GetMapping("/wrong-problems")
    public ResponseEntity<?> getWrongProblems(@CurrentUser CustomUserDetails customUserDetails) {
        List<GetWrongProblemsDto> getWrongProblemsDtos = problemService.getWrongProblems(customUserDetails);
        return ResponseEntity.status(HttpStatus.OK).body(getWrongProblemsDtos);
    }

    @GetMapping
    public ResponseEntity<?> getProblems() {
        List<Map<String, Object>> problems = new ArrayList<>();

        // 문제 1
        Map<String, Object> problem1 = new HashMap<>();
        problem1.put("problem", "다음 글의 중심 내용을 고르세요.\n'철수는 아침에 일어나서 학교에 갔습니다. \n학교에서 수업을 듣고 집에 돌아왔습니다.'");
        problem1.put("1", "철수는 아침에 일어났다.");
        problem1.put("2", "철수는 학교에 갔다.");
        problem1.put("3", "철수는 집에 돌아왔다.");
        problem1.put("answer", "2");
        problem1.put("explanation", "글의 중심 내용은 철수가 학교에 갔다는 것입니다.");
        problems.add(problem1);

        // 문제 2
        Map<String, Object> problem2 = new HashMap<>();
        problem2.put("problem", "다음 글의 세부 정보를 고르세요.\n'영희는 도서관에서 책을 빌렸습니다.\n 그녀는 매일 2시간씩 책을 읽습니다.'");
        problem2.put("1", "영희는 서점에서 책을 샀다.");
        problem2.put("2", "영희는 매일 2시간씩 책을 읽는다.");
        problem2.put("3", "영희는 책을 집에서 읽는다.");
        problem2.put("answer", "2");
        problem2.put("explanation", "글에서 영희가 매일 2시간씩 책을 읽는다고 명시되어 있습니다.");
        problems.add(problem2);

        // 문제 3
        Map<String, Object> problem3 = new HashMap<>();
        problem3.put("problem", "다음 중 '기쁘다'와 동의어인 단어를 고르세요.");
        problem3.put("1", "슬프다");
        problem3.put("2", "화나다");
        problem3.put("3", "행복하다");
        problem3.put("answer", "3");
        problem3.put("explanation", "'행복하다'는 '기쁘다'와 의미가 유사한 동의어입니다.");
        problems.add(problem3);

        // 문제 4
        Map<String, Object> problem4 = new HashMap<>();
        problem4.put("problem", "다음 중 '어렵다'의 반의어를 고르세요.");
        problem4.put("1", "쉬운");
        problem4.put("2", "힘든");
        problem4.put("3", "즐거운");
        problem4.put("answer", "1");
        problem4.put("explanation", "'쉬운'은 '어렵다'의 반의어입니다.");
        problems.add(problem4);

        // 문제 5
        Map<String, Object> problem5 = new HashMap<>();
        problem5.put("problem", "다음 중 동음이의어가 사용된 문장을 고르세요.");
        problem5.put("1", "나는 아침에 밥을 먹었다.");
        problem5.put("2", "나는 배를 타고 바다로 나갔다.");
        problem5.put("3", "나는 손으로 그림을 그렸다.");
        problem5.put("answer", "2");
        problem5.put("explanation", "'배'는 '과일'과 '몸의 일부분'을 의미하는 동음이의어입니다.");
        problems.add(problem5);

        // 문제 6
        Map<String, Object> problem6 = new HashMap<>();
        problem6.put("problem", "다음 중 맞춤법이 올바른 문장을 고르세요.");
        problem6.put("1", "나는 어제 친구와 만났읍니다.");
        problem6.put("2", "나는 어제 친구와 만났습니다.");
        problem6.put("3", "나는 어제 친구와 만났습니가.");
        problem6.put("answer", "2");
        problem6.put("explanation", "'만났습니다'가 맞는 표현입니다.");
        problems.add(problem6);

        // 문제 7
        Map<String, Object> problem7 = new HashMap<>();
        problem7.put("problem", "다음 중 띄어쓰기가 올바른 문장을 고르세요.");
        problem7.put("1", "우리는 오늘도 학교에 갑니다.");
        problem7.put("2", "우리는 오늘 도학교에 갑니다.");
        problem7.put("3", "우리는오늘도학교에 갑니다.");
        problem7.put("answer", "1");
        problem7.put("explanation", "'우리는 오늘도 학교에 갑니다.'가 올바른 띄어쓰기입니다.");
        problems.add(problem7);

        // 문제 8
        Map<String, Object> problem8 = new HashMap<>();
        problem8.put("problem", "다음 중 관용구 '손이 크다'의 뜻을 고르세요.");
        problem8.put("1", "손이 실제로 크다.");
        problem8.put("2", "돈을 많이 쓴다.");
        problem8.put("3", "일을 많이 한다.");
        problem8.put("answer", "2");
        problem8.put("explanation", "'손이 크다'는 돈을 많이 쓴다는 뜻의 관용구입니다.");
        problems.add(problem8);

        // 문제 9
        Map<String, Object> problem9 = new HashMap<>();
        problem9.put("problem", "다음 중 '발 없는 말이 천리 간다'의 뜻을 고르세요.");
        problem9.put("1", "말이 빠르다.");
        problem9.put("2", "말이 없다.");
        problem9.put("3", "소문은 빨리 퍼진다.");
        problem9.put("answer", "3");
        problem9.put("explanation", "'발 없는 말이 천리 간다'는 소문은 빨리 퍼진다는 뜻의 속담입니다.");
        problems.add(problem9);

        // 문제 10
        Map<String, Object> problem10 = new HashMap<>();
        problem10.put("problem", "다음 중 접속 표현이 올바른 문장을 고르세요.");
        problem10.put("1", "비가 오지만 우산을 챙겼다.");
        problem10.put("2", "비가 와서 우산을 챙겼다.");
        problem10.put("3", "비가 오다 우산을 챙겼다.");
        problem10.put("answer", "2");
        problem10.put("explanation", "'비가 와서'는 원인과 결과를 나타내는 접속 표현으로 자연스럽습니다.");
        problems.add(problem10);

        // 전체 JSON 객체로 변환
        Map<String, Object> response = new HashMap<>();
        response.put("problems", problems);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
