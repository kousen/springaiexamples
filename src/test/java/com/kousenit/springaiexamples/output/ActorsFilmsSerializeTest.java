package com.kousenit.springaiexamples.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActorsFilmsSerializeTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deserialize() throws Exception {
        String margotRobbie = """
                {
                  "actor": "Margot Robbie",
                  "movies": [
                    "I, Tonya",
                    "Once Upon a Time in Hollywood",
                    "Bombshell",
                    "Birds of Prey"
                   ]
                 }
                 """;

        ActorsFilms films = objectMapper.readValue(margotRobbie, ActorsFilms.class);
        assertNotNull(films);
        assertEquals("Margot Robbie", films.actor());
        assertEquals(4, films.movies().size());
    }

}