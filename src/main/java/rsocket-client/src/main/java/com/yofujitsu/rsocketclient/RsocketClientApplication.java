package com.yofujitsu.rsocketclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
public class RsocketClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RsocketClientApplication.class, args);
	}

}
