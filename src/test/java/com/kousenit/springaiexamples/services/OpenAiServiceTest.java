package com.kousenit.springaiexamples.services;

import com.kousenit.springaiexamples.output.ActorsFilms;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}