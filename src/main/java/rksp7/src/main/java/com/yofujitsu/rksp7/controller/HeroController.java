package com.yofujitsu.rksp7.controller;

import com.yofujitsu.rksp7.entity.Hero;
import com.yofujitsu.rksp7.service.HeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/heroes")
public class HeroController {

    private final HeroService heroService;

    @GetMapping()
    public Flux<Hero> getHeroes() {
        return heroService.getHeroes();
    }

    @GetMapping("/{name}")
    public Mono<Hero> getHeroByName(@PathVariable String name) {
        return heroService.getHeroByName(name);
    }

    @PostMapping("/add")
    public Mono<Hero> addHero(@RequestBody Hero hero) {
        return heroService.addHero(hero);
    }

    @DeleteMapping("/delete/{name}")
    public Mono<Void> deleteHero(@PathVariable String name) {
        return heroService.deleteHero(name);
    }

    @PutMapping("/update")
    public Mono<Hero> updateHero(@RequestBody Hero hero) {
        return heroService.updateHero(hero);
    }

    @PostMapping("/add-multiple")
    public Flux<Hero> addMultipleHeroes(@RequestBody List<Hero> heroes) {
        return heroService.addMultipleHeroes(heroes);
    }


}
