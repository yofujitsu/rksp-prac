package com.yofujitsu.rsocketclient.controller;

import com.yofujitsu.rsocketclient.entity.Agent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/agents")
@RequiredArgsConstructor
public class RequestResponseController {

    private final RSocketRequester rSocketRequester;

    @GetMapping("/{name}")
    public Mono<Agent> getAgent(@PathVariable String name) {
        return rSocketRequester.route("getAgent")
                .data(name)
                .retrieveMono(Agent.class);
    }

    @PostMapping
    public Mono<Agent> createAgent(@RequestBody Agent agent) {
        return rSocketRequester.route("createAgent")
                .data(agent)
                .retrieveMono(Agent.class);
    }
}
