package com.yofujitsu.rksp7.repos;

import com.yofujitsu.rksp7.entity.Hero;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface HeroRepository extends R2dbcRepository<Hero, Long> {
    Mono<Hero> findByName(String name);

    Mono<Void> deleteByName(String name);
}
