package com.souza.heroesapi.controller;

import com.souza.heroesapi.constants.HeroesConstant;
import com.souza.heroesapi.document.Heroes;
import com.souza.heroesapi.service.HeroesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
public class HeroesController {

    @Autowired
    private HeroesService heroesService;

    private static final Logger heroesControllerLog = LoggerFactory.getLogger(HeroesController.class);

    @GetMapping(HeroesConstant.HEROES_ENDPOINT_LOCAL)
    public Flux<Heroes> getAllItems(){
        heroesControllerLog.info("Request the list of all heroes");
        return heroesService.findAll();
    }

    @GetMapping(HeroesConstant.HEROES_ENDPOINT_LOCAL + "/{id}")
    public Mono<ResponseEntity<Heroes>> findById(@PathVariable String id){
        heroesControllerLog.info("Requesting the hero with id {}", id);
        return heroesService.findByIdHero(id)
                    .map((i) -> new ResponseEntity<>(i, HttpStatus.OK))
                    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(HeroesConstant.HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<Heroes> save(@RequestBody Heroes heroes){
        heroesControllerLog.info("A new hero was created");
        return heroesService.save(heroes);
    }

    @DeleteMapping(HeroesConstant.HEROES_ENDPOINT_LOCAL+"/{id}")
    public Mono<HttpStatus> deleteById(@PathVariable String id){
        heroesService.deleteById(id);
        heroesControllerLog.info("deleting the hero with id {}", id);
        return Mono.just(HttpStatus.CONTINUE);
    }
}
