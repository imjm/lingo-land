package com.ssafy.a603.lingoland.problem.repository;

import com.ssafy.a603.lingoland.problem.entity.ProblemMember;
import com.ssafy.a603.lingoland.problem.entity.ProblemMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemMemberRepository extends JpaRepository<ProblemMember, ProblemMemberId> {
}
