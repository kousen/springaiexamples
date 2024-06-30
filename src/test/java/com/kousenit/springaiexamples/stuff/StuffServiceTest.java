package com.kousenit.springaiexamples.stuff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StuffServiceTest {
    @Autowired
    private StuffService stuffService;

    @Test
    void askQuestion() {
        String response = stuffService.askQuestion("""
                Who won the gold medal in curling
                in the mixed-doubles event at the
                2022 Winter Olympics?
                """);
        assertNotNull(response);
        System.out.println(response);
        assertThat(response).contains("Constantini", "Mosaner");
    }

    @Test
    void medalists() {
        String response = stuffService.askQuestion("""
                Give me a list of all the medalists
                in curling at the 2022 Winter Olympics.
                """);
        assertNotNull(response);
        System.out.println(response);
        assertThat(response).contains("Constantini", "Mosaner");
    }
}