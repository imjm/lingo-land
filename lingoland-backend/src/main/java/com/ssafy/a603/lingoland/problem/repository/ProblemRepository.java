package com.ssafy.a603.lingoland.problem.repository;

import com.ssafy.a603.lingoland.problem.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Integer> {
}
