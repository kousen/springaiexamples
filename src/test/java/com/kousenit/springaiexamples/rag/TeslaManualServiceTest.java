package com.kousenit.springaiexamples.rag;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("rag")
class TeslaManualServiceTest {

    @Autowired
    private TeslaManualService service;

    @Test
    void ask() {
        String response = service.ask("How do I open the frunk?");
        System.out.println(response);
        assertTrue(response.contains("frunk"));
    }

}