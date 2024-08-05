package com.ssafy.a603.lingoland.problem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class CreateGameResultsDto {

    private List<ProblemDto> problemList;
}
