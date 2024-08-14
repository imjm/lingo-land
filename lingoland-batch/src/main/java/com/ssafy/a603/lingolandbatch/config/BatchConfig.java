package com.ssafy.a603.lingolandbatch.config;


import com.ssafy.a603.lingolandbatch.tasklet.BatchTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);
    private final BatchTasklet batchTasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private static int executionCnt = 0;

    @Value("${spring.executionCnt}")
    private static int batchExecutionMax;

    @Bean
    public Job batchJob() {
        return new JobBuilder("batchJob", jobRepository)
                .listener(jobExecutionListener())
                .start(batchStep())
                .next(jobExecutionDecider())
                .from(jobExecutionDecider()).on("CONTINUE").to(batchStep())
                .from(jobExecutionDecider()).on("FINISH").end()
                .build()
                .build();
    }

    @Bean
    public Step batchStep(){

        log.info("step cont {}", executionCnt);
        return new StepBuilder("batchStep", jobRepository).tasklet(batchTasklet, transactionManager).build();
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobExecutionListener(){
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("job start {}", LocalDateTime.now());
                executionCnt = 0;
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                log.info("job end {}", LocalDateTime.now());
            }
        };
    }

    @Bean
    public JobExecutionDecider jobExecutionDecider(){
        return ((jobExecution, stepExecution) -> {
            executionCnt++;
            if (executionCnt < batchExecutionMax) {
                return new FlowExecutionStatus("CONTINUE");
            } else{
                return new FlowExecutionStatus("FINISH");
            }
        });
    }

    public static void setExecutionCntWhenError(){
        executionCnt = batchExecutionMax;
    }
}
