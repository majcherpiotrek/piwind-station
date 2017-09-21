package com.piotrmajcher.piwind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
@EnableWebMvc
public class PiwindApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiwindApplication.class, args);
	}
}
