package com.yofujitsu.rsocketclient.controller;

import com.yofujitsu.rsocketclient.entity.Agent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/agents")
@RequiredArgsConstructor
public class RequestStreamController {

    private final RSocketRequester rSocketRequester;

    @GetMapping
    public Flux<Agent> getAgents() {
        return rSocketRequester.route("getAll")
                .data("")
                .retrieveFlux(Agent.class);
    }
}
