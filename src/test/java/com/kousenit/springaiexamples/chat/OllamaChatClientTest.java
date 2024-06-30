package com.kousenit.springaiexamples.chat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OllamaChatClientTest {
    @Autowired
    private OllamaChatModel client;

    @Test
    void testChatClient() {
        System.out.println(client.call("Why is the sky blue?"));
    }
}
