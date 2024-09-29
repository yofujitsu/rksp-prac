package com.yofujitsu.rsocket.service;


import com.yofujitsu.rsocket.entity.Agent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AgentService {

    Mono<Agent> findByName(String name);
    Mono<Agent> create(Agent agent);

    Mono<Agent> update(Agent agent);

    Mono<Void> delete(UUID id);
    Flux<Agent> findAll();
}
