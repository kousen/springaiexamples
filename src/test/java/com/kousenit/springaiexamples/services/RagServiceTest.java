package com.kousenit.springaiexamples.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RagServiceTest {
    @Autowired
    private RagService ragService;

    @Test
    void queryVectorStore() {
        String response = ragService.queryVectorStore(
                "What high performance road bikes are available?");
        System.out.println(response);
        assertThat(response).contains("high", "performance");
    }
}