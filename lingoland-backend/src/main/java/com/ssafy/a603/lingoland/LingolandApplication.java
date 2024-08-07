package com.ssafy.a603.lingoland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LingolandApplication {

	public static void main(String[] args) {
		SpringApplication.run(LingolandApplication.class, args);
	}

}
