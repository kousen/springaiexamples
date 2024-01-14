package com.kousenit.springaiexamples.output;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActorServiceTest {
    @Autowired
    private ActorService service;

    @Test
    void getActorFilms() {
        var films = service.getActorFilms("Tom Hanks");
        films.movies().forEach(System.out::println);
        assertEquals("Tom Hanks", films.actor());
        assertTrue(films.movies().contains("Forrest Gump"));
        assertTrue(films.movies().contains("Cast Away"));
        assertTrue(films.movies().contains("Apollo 13"));
    }
}