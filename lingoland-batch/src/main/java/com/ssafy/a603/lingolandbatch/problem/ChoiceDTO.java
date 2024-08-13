package com.ssafy.a603.lingolandbatch.problem;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ChoiceDTO {
    private int num;
    private String text;
}