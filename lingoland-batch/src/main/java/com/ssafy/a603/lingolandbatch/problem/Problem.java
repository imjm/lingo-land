package com.ssafy.a603.lingolandbatch.problem;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id
    private Integer id;

    private long correctAnswerCount;

    private long incorrectAnswerCount;

    private LocalDateTime deletedAt;

    private boolean isDeleted;

    private String inspector;

    private String creator;

    private Detail detail;

    @Builder
    public static class Detail {
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

    public Problem fromDTO(ProblemDTO problemDTO){
        return Problem.builder()
                .correctAnswerCount(0)
                .incorrectAnswerCount(0)
                .isDeleted(false)
                .inspector(null)
                .creator("ChatGPT")

                .build();
    }

}
