package com.ssafy.a603.lingolandbatch.problem;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends ReactiveCrudRepository<Problem, Integer> {
}
