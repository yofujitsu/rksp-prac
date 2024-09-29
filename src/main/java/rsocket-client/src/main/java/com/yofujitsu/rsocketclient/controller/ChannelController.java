package com.yofujitsu.rsocketclient.controller;

import com.yofujitsu.rsocketclient.entity.Agent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/agents")
@RequiredArgsConstructor
public class ChannelController {

    private final RSocketRequester rSocketRequester;

    @PostMapping("/agentChannel")
    public Flux<Agent> AgentChannel(@RequestBody List<Agent> agentsList) {
        Flux<Agent> agents = Flux.fromIterable(agentsList);
        return rSocketRequester.route("AgentChannel")
                .data(agents)
                .retrieveFlux(Agent.class);
    }

}
