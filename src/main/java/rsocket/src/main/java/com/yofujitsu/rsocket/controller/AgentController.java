package com.yofujitsu.rsocket.controller;

import com.yofujitsu.rsocket.entity.Agent;
import com.yofujitsu.rsocket.repository.AgentRepository;
import com.yofujitsu.rsocket.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;
    private final AgentRepository agentRepository;

    @MessageMapping("getAgent")
    public Mono<Agent> findByName(String name) {
        return agentService.findByName(name);
    }

    @MessageMapping("createAgent")
    public Mono<Agent> create(Agent agent) {
        return agentService.create(agent);
    }

    @MessageMapping("updateAgent")
    public Mono<Agent> update(Agent agent) {
        return agentService.update(agent);
    }

    @MessageMapping("deleteAgent")
    public Mono<Void> delete(UUID id) {
        return agentService.delete(id);
    }

    @MessageMapping("getAll")
    public Flux<Agent> findAll() {
        return agentService.findAll();
    }

    @MessageMapping("AgentChannel")
    public Flux<Agent> AgentChannel(Flux<Agent> agentFlux) {
        return agentFlux.flatMap(agent -> Mono.fromCallable(() -> agentRepository.save(agent)))
                .collectList().flatMapMany(Flux::fromIterable);
    }

}
