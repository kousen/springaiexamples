package com.kousenit.springaiexamples.rag;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureWebTestClient(timeout = "36000")
class PossServiceTest {
    @Autowired
    private PossService service;

    @Test
    void retrieve() {
        Generation generation = service.retrieve(
                """
                Please provide several multiple choice questions 
                about this chapter.""");
        assertNotNull(generation);
        System.out.println(generation);
    }
}