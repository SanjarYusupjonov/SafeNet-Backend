package com.safenet.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SafenetBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafenetBackendApplication.class, args);
	}

}
