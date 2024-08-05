package com.ssafy.a603.lingoland.problem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/problems")
public class ProblemController {

    // should be revised to find random problem of specific kind.

    @GetMapping
    public ResponseEntity<?> getProblems(){
        String json = "{\n" +
                "  \"problems\": [\n" +
                "    {\n" +
                "      \"problem\": \"다음 글의 중심 내용을 고르세요.\n'철수는 아침에 일어나서 학교에 갔습니다. \n학교에서 수업을 듣고 집에 돌아왔습니다.'\",\n" +
                "      \"1\": \"철수는 아침에 일어났다.\",\n" +
                "      \"2\": \"철수는 학교에 갔다.\",\n" +
                "      \"3\": \"철수는 집에 돌아왔다.\",\n" +
                "      \"answer\": \"2\",\n" +
                "      \"explanation\": \"글의 중심 내용은 철수가 학교에 갔다는 것입니다.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"problem\": \"다음 글의 세부 정보를 고르세요.\\n'영희는 도서관에서 책을 빌렸습니다.\n 그녀는 매일 2시간씩 책을 읽습니다.'\",\n" +
                "      \"1\": \"영희는 서점에서 책을 샀다.\",\n" +
                "      \"2\": \"영희는 매일 2시간씩 책을 읽는다.\",\n" +
                "      \"3\": \"영희는 책을 집에서 읽는다.\",\n" +
                "      \"answer\": \"2\",\n" +
                "      \"explanation\": \"글에서 영희가 매일 2시간씩 책을 읽는다고 명시되어 있습니다.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"problem\": \"다음 중 '기쁘다'와 동의어인 단어를 고르세요.\",\n" +
                "      \"1\": \"슬프다\",\n" +
                "      \"2\": \"화나다\",\n" +
                "      \"3\": \"행복하다\",\n" +
                "      \"answer\": \"3\",\n" +
                "      \"explanation\": \"'행복하다'는 '기쁘다'와 의미가 유사한 동의어입니다.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"problem\": \"다음 중 '어렵다'의 반의어를 고르세요.\",\n" +
                "      \"1\": \"쉬운\",\n" +
                "      \"2\": \"힘든\",\n" +
                "      \"3\": \"즐거운\",\n" +
                "      \"answer\": \"1\",\n" +
                "      \"explanation\": \"'쉬운'은 '어렵다'의 반의어입니다.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"problem\": \"다음 중 동음이의어가 사용된 문장을 고르세요.\",\n" +
                "      \"1\": \"나는 아침에 밥을 먹었다.\",\n" +
                "      \"2\": \"나는 배를 타고 바다로 나갔다.\",\n" +
                "      \"3\": \"나는 손으로 그림을 그렸다.\",\n" +
                "      \"answer\": \"2\",\n" +
                "      \"explanation\": \"'배'는 '과일'과 '몸의 일부분'을 의미하는 동음이의어입니다.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"problem\": \"다음 중 맞춤법이 올바른 문장을 고르세요.\",\n" +
                "      \"1\": \"나는 어제 친구와 만났읍니다.\",\n" +
                "      \"2\": \"나는 어제 친구와 만났습니다.\",\n" +
                "      \"3\": \"나는 어제 친구와 만났습니가.\",\n" +
                "      \"answer\": \"2\",\n" +
                "      \"explanation\": \"'만났습니다'가 맞는 표현입니다.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"problem\": \"다음 중 띄어쓰기가 올바른 문장을 고르세요.\",\n" +
                "      \"1\": \"우리는 오늘도 학교에 갑니다.\",\n" +
                "      \"2\": \"우리는 오늘 도학교에 갑니다.\",\n" +
                "      \"3\": \"우리는오늘도학교에 갑니다.\",\n" +
                "      \"answer\": \"1\",\n" +
                "      \"explanation\": \"'우리는 오늘도 학교에 갑니다.'가 올바른 띄어쓰기입니다.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"problem\": \"다음 중 관용구 '손이 크다'의 뜻을 고르세요.\",\n" +
                "      \"1\": \"손이 실제로 크다.\",\n" +
                "      \"2\": \"돈을 많이 쓴다.\",\n" +
                "      \"3\": \"일을 많이 한다.\",\n" +
                "      \"answer\": \"2\",\n" +
                "      \"explanation\": \"'손이 크다'는 돈을 많이 쓴다는 뜻의 관용구입니다.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"problem\": \"다음 중 '발 없는 말이 천리 간다'의 뜻을 고르세요.\",\n" +
                "      \"1\": \"말이 빠르다.\",\n" +
                "      \"2\": \"말이 없다.\",\n" +
                "      \"3\": \"소문은 빨리 퍼진다.\",\n" +
                "      \"answer\": \"3\",\n" +
                "      \"explanation\": \"'발 없는 말이 천리 간다'는 소문은 빨리 퍼진다는 뜻의 속담입니다.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"problem\": \"다음 중 접속 표현이 올바른 문장을 고르세요.\",\n" +
                "      \"1\": \"비가 오지만 우산을 챙겼다.\",\n" +
                "      \"2\": \"비가 와서 우산을 챙겼다.\",\n" +
                "      \"3\": \"비가 오다 우산을 챙겼다.\",\n" +
                "      \"answer\": \"2\",\n" +
                "      \"explanation\": \"'비가 와서'는 원인과 결과를 나타내는 접속 표현으로 자연스럽습니다.\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
