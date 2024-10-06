package com.yofujitsu.rksp7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class Rksp7Application {

	public static void main(String[] args) {
		SpringApplication.run(Rksp7Application.class, args);
	}

}
