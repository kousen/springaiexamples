package com.kousenit.springaiexamples.helloworld;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
class SimpleAiControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    void completion() {
        client.get()
                .uri("/ai/generate")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);

    }
}