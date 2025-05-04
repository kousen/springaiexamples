package com.kousenit.springaiexamples.output;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActorServiceTest {
    @Autowired
    private ActorService service;

    @Test
    void getActorFilms() {
        ActorsFilms actorFilms = service.getActorFilms("Margot Robbie");
        System.out.println("Films for Margot Robbie:");
        actorFilms.movies().forEach(System.out::println);
    }
}