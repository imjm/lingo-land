package com.ssafy.a603.lingolandbatch.problem;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
    public class Detail {
        private String problem;
        private List<Choice> choices;
        private int answer;
        private String explanation;

        @Getter
        @NoArgsConstructor(access = AccessLevel.PROTECTED)
        @AllArgsConstructor
        @Builder
        public static class Choice {
            private int num;
            private String text;
        }
    }