package com.ssafy.a603.lingoland.writingGame.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
	private int CORE_POOL_SIZE = 3;
	private int MAX_POOL_SIZE = 10;
	private int QUEUE_CAPACITY = 10000;

	@Bean(name = "sampleExecutor")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

		taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
		taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
		taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
		taskExecutor.setThreadNamePrefix("Executor-");

		return taskExecutor;
	}
}