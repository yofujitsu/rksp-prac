package com.yofujitsu.rksp7.service;

import com.yofujitsu.rksp7.entity.Hero;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface HeroService {

    Mono<Hero> getHeroByName(String name);

    Mono<Hero> addHero(Hero hero);

    Mono<Void> deleteHero(String name);

    Mono<Hero> updateHero(Hero hero);

    Flux<Hero> getHeroes();

    Flux<Hero> addMultipleHeroes(List<Hero> heroes);
}
