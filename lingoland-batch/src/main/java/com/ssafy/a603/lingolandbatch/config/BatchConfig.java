package com.ssafy.a603.lingolandbatch.config;


import com.ssafy.a603.lingolandbatch.tasklet.HelloworldTasklet;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);
    private final HelloworldTasklet helloworldTasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    private static int executionCnt = 0;

    @Bean
    public Job helloworldJob() {
        return new JobBuilder("helloworldJob", jobRepository)
                .listener(jobExecutionListener())
                .start(helloworldStep())
                .next(jobExecutionDecider())
                .from(jobExecutionDecider()).on("CONTINUE").to(helloworldStep())
                .from(jobExecutionDecider()).on("FINISH").end()
                .build()
                .build();
    }

    @Bean
    public Step helloworldStep(){
        return new StepBuilder("helloworldStep", jobRepository).tasklet(helloworldTasklet, transactionManager).build();
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobExecutionListener(){
            @Override
            public void beforeJob(JobExecution jobExecution) {
                log.info("job start");
                executionCnt = 0;
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                log.info("job end");
            }
        };
    }

    @Bean
    public JobExecutionDecider jobExecutionDecider(){
        return ((jobExecution, stepExecution) -> {
            executionCnt++;
            if (executionCnt < 1) {
                return new FlowExecutionStatus("CONTINUE");
            } else{
                return new FlowExecutionStatus("FINISH");
            }
        });
    }

    public static void setExecutionCntWhenError(){
        executionCnt = 10;
    }
}
