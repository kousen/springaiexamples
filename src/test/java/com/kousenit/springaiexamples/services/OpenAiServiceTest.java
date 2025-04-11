package com.kousenit.springaiexamples.services;

import com.kousenit.springaiexamples.output.ActorsFilms;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OpenAiServiceTest {
    @Autowired
    private OpenAiService service;

    @Test
    void chatWithPromptTemplate() {
        String response = service.chatWithPromptTemplate("funny", "pirates");
        assertNotNull(response);
        System.out.println(response);
    }

    @Test
    void chat() {
        String response = service.chat(
                """
                    Tell me about 3 famous pirates from the Golden Age of Piracy
                    and what they did.
                    """
        );
        assertNotNull(response);
        System.out.println(response);
    }

    @Test
    void getActorsFilms() {
        ActorsFilms actorsFilms = service.getActorsFilms("Margot Robbie");
        assertNotNull(actorsFilms);
        System.out.println("For " + actorsFilms.actor() + ":");
        actorsFilms.movies().stream()
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    void getFilmsForActor() {
        List<String> films = service.getFilmsForActor("Scarlett Johansson");
        System.out.println(films);
        assertThat(films).isNotEmpty()
                .contains("The Avengers (2012)", "Iron Man 2 (2010)");
    }

    @Test
    void getActorFilmsListAsync() {
        service.getActorFilmsListAsync("Margot Robbie", "Tom Hanks")
                .doOnNext(System.out::print)
                .blockLast();
        System.out.println("Done.");
    }
}