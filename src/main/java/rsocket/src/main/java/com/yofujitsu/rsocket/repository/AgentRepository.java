package com.yofujitsu.rsocket.repository;

import com.yofujitsu.rsocket.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgentRepository extends JpaRepository<Agent, UUID> {

    Agent findByName(String name);
}
