package com.yofujitsu.rksp7.service;

import com.yofujitsu.rksp7.entity.Hero;
import com.yofujitsu.rksp7.repos.HeroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;

    @Override
    public Mono<Hero> getHeroByName(String name) {
        return heroRepository.findByName(name);
    }

    @Override
    @Transactional
    public Mono<Hero> addHero(Hero hero) {
        return Mono.just(hero)
                .flatMap(heroRepository::save)
                .onErrorComplete();
    }

    @Override
    public Mono<Void> deleteHero(String name) {
        return heroRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public Mono<Hero> updateHero(Hero hero) {
        return Mono.just(hero)
                .flatMap(heroRepository::save)
                .onErrorComplete();
    }

    @Override
    public Flux<Hero> getHeroes() {
        return heroRepository.findAll()
                .onErrorComplete()
                .onBackpressureBuffer();
    }

    @Override
    public Flux<Hero> addMultipleHeroes(List<Hero> heroes) {
       Flux<Hero> flux = Flux.fromIterable(heroes);
       return flux
               .flatMap(heroRepository::save)
               .onErrorComplete();
    }
}
