package com.ssafy.a603.lingoland.problem.service;

import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.problem.dto.CreateGameResultsDto;
import com.ssafy.a603.lingoland.problem.dto.GetWrongProblemsDto;

import java.util.List;

public interface ProblemService {

    void createGameResults(CreateGameResultsDto createGameResultsDto, CustomUserDetails customUserDetails);

    List<GetWrongProblemsDto> getWrongProblems(CustomUserDetails customUserDetails);

    List<GetWrongProblemsDto> getWrongProblemsByGroupLeader(Integer groupId, Integer memberId, CustomUserDetails customUserDetails);

    void deleteWrongProblems(Integer problemId, CustomUserDetails customUserDetails);


}
