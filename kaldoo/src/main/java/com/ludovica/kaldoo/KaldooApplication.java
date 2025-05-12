package com.ludovica.kaldoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KaldooApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaldooApplication.class, args);
	}

}
