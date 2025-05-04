package com.kousenit.springaiexamples.output;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
class ActorControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void generateUsingDefaults() {
        webTestClient.get()
                .uri("/actor")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(String.class)
                .value(System.out::println);
    }

}