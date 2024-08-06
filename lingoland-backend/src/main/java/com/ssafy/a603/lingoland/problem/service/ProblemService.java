package com.ssafy.a603.lingoland.problem.service;

import com.ssafy.a603.lingoland.member.entity.Member;
import com.ssafy.a603.lingoland.member.repository.MemberRepository;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import com.ssafy.a603.lingoland.problem.dto.CreateGameResultsDto;
import com.ssafy.a603.lingoland.problem.dto.ProblemDto;
import com.ssafy.a603.lingoland.problem.entity.Problem;
import com.ssafy.a603.lingoland.problem.entity.ProblemMember;
import com.ssafy.a603.lingoland.problem.entity.ProblemMemberId;
import com.ssafy.a603.lingoland.problem.repository.ProblemMemberRepository;
import com.ssafy.a603.lingoland.problem.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final ProblemMemberRepository problemMemberRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createGameResults(CreateGameResultsDto createGameResultsDto, CustomUserDetails customUserDetails) {
        Member member = memberRepository.findById(customUserDetails.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member"));

        for(ProblemDto problemDto : createGameResultsDto.getProblemList()) {
            Problem problem = problemRepository.findById(problemDto.getProblemId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid problem id: " + problemDto.getProblemId()));

            ProblemMemberId problemMemberId = new ProblemMemberId(member, problem);

            ProblemMember problemMember = ProblemMember.builder()
                    .id(problemMemberId)
                    .submittedAnswer(problemDto.getAnswer())
                    .build();

            problemMemberRepository.save(problemMember);

            // TODO 맞춘 문제 틀린 문제 update
        }

    }
}
