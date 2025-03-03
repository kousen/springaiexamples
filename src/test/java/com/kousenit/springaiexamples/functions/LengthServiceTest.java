package com.kousenit.springaiexamples.functions;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LengthServiceTest {

    @Autowired
    private ChatModel chatModel;

    @Test
    void testLengthService() {
        String response = ChatClient.create(chatModel).prompt()
                .advisors(new SimpleLoggerAdvisor())
                .tools(new Tools())
                .user("Calculate the length of this expression: 'Hello, world!'")
                .call()
                .content();
        assertNotNull(response);
        assertFalse(response.isEmpty());
        System.out.println(response);
    }
}