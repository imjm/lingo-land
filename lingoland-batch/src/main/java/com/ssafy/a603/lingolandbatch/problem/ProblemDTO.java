package com.ssafy.a603.lingolandbatch.problem;

import lombok.Getter;

import java.util.List;

public class ProblemDTO {

    private Problem.Detail detail;

    @Getter
    public static class DetailDTO {
        private String problem;
        private List<Problem.Detail.Choice> choices;
        private int answer;
        private String explanation;

        @Getter
        public static class Choice {
            private int num;
            private String text;
        }

        @Override
        public String toString(){

            return "problem " + getProblem() + " answer " + getAnswer()
                    + " explanation " + getExplanation() + " choice num " + choices.size();
        }
    }
}
