package com.yofujitsu.rsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class RsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(RsocketApplication.class, args);
	}

}
