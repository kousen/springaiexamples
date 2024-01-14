package com.kousenit.springaiexamples.rag;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RagServiceTest {
    @Autowired
    private RagService service;

    @Test
    void retrieve() {
        Generation generation = service.retrieve("Tell me some details about the SwiftRide Hybrid");
        assertNotNull(generation);
        System.out.println(generation);
    }
}