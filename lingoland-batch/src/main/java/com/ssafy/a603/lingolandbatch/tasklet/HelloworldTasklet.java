package com.ssafy.a603.lingolandbatch.tasklet;

import com.ssafy.a603.lingolandbatch.problem.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class HelloworldTasklet implements Tasklet {

    private final ProblemService problemService;
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("tasklet start");
        for(int i = 0; i < 1; i++){
            System.out.println("hello world " + i);
            problemService.makeProblem();
            problemService.test1();
            System.out.println("*****************************");
            problemService.test2();
        }
        log.info("tasklet end");
        return RepeatStatus.FINISHED;
    }
}
