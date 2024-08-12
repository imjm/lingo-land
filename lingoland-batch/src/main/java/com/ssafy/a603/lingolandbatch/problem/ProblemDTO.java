package com.ssafy.a603.lingolandbatch.problem;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@ToString
public class ProblemDTO {

    private long correctAnswerCount;

    private long incorrectAnswerCount;

    private LocalDateTime deletedAt;

    private boolean isDeleted;

    private String inspector;

    private String creator;

    private DetailDTO detailDTO;

}
