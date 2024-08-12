package com.ssafy.a603.lingolandbatch.problem;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class DetailDTO {
    private String problem;
    private List<ChoiceDTO> choiceDTOS;
    private int answer;
    private String explanation;

}