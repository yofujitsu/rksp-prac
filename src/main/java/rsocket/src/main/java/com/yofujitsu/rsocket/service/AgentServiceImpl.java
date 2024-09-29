package com.yofujitsu.rsocket.service;

import com.yofujitsu.rsocket.entity.Agent;
import com.yofujitsu.rsocket.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgentServiceImpl implements AgentService{

    private final AgentRepository agentRepository;

    @Override
    public Mono<Agent> findByName(String name) {
        return Mono.justOrEmpty(agentRepository.findByName(name));
    }

    @Override
    public Mono<Agent> create(Agent agent) {
        return Mono.justOrEmpty(agentRepository.save(agent));
    }

    @Override
    public Mono<Agent> update(Agent agent) {
        return Mono.justOrEmpty(agentRepository.save(agent));
    }

    @Override
    public Mono<Void> delete(UUID id) {
        agentRepository.deleteById(id);
        return Mono.empty();
    }

    @Override
    public Flux<Agent> findAll() {
        return Flux.fromIterable(agentRepository.findAll());
    }
}
