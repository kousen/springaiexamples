package com.kousenit.springaiexamples.chat;

import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OpenAIChatClientTest {
    @Autowired
    private OpenAiChatClient client;

    @Test
    void testChatClient() {
        System.out.println(client.call("Why is the sky blue?"));
    }
}
