package com.yofujitsu.rksp7;

import com.yofujitsu.rksp7.entity.Hero;
import com.yofujitsu.rksp7.repos.HeroRepository;
import com.yofujitsu.rksp7.service.HeroService;
import com.yofujitsu.rksp7.service.HeroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class Rksp7ApplicationTests {

    @Mock
    private HeroRepository heroRepository;

    @InjectMocks
    private HeroServiceImpl heroService;

    private Hero hero;

    @BeforeEach
    public void setup() {
        hero = new Hero(1L, "Pudge", "STRENGTH", 5, true);
    }

    @Test
    public void testGetHeroByName() {
        when(heroRepository.findByName("Pudge")).thenReturn(Mono.just(hero));

        Mono<Hero> result = heroService.getHeroByName("Pudge");

        StepVerifier.create(result)
                .expectNext(hero)
                .verifyComplete();
    }

    @Test
    public void testAddHero() {
        when(heroRepository.save(any(Hero.class))).thenReturn(Mono.just(hero));

        Mono<Hero> result = heroService.addHero(hero);

        StepVerifier.create(result)
                .expectNext(hero)
                .verifyComplete();

        verify(heroRepository, times(1)).save(hero);
    }

    @Test
    public void testDeleteHero() {
        when(heroRepository.deleteByName("Pudge")).thenReturn(Mono.empty());

        Mono<Void> result = heroService.deleteHero("Pudge");

        StepVerifier.create(result)
                .verifyComplete();

        verify(heroRepository, times(1)).deleteByName("Pudge");
    }

    @Test
    public void testUpdateHero() {
        when(heroRepository.save(any(Hero.class))).thenReturn(Mono.just(hero));

        Mono<Hero> result = heroService.updateHero(hero);

        StepVerifier.create(result)
                .expectNext(hero)
                .verifyComplete();

        verify(heroRepository, times(1)).save(hero);
    }

    @Test
    public void testGetHeroes() {
        when(heroRepository.findAll()).thenReturn(Flux.just(hero));

        Flux<Hero> result = heroService.getHeroes();

        StepVerifier.create(result)
                .expectNext(hero)
                .verifyComplete();
    }

    @Test
    public void testAddMultipleHeroes() {
        List<Hero> heroes = Arrays.asList(hero, new Hero(2L, "Rikimaru", "AGILITY", 4, true));
        when(heroRepository.save(any(Hero.class)))
                .thenReturn(Mono.just(hero))
                .thenReturn(Mono.just(new Hero(2L, "Rikimaru", "AGILITY", 4, true)));

        Flux<Hero> result = heroService.addMultipleHeroes(heroes);

        StepVerifier.create(result)
                .expectNext(hero)
                .expectNext(new Hero(2L, "Rikimaru", "AGILITY", 4, true))
                .verifyComplete();

        verify(heroRepository, times(2)).save(any(Hero.class));
    }

}
