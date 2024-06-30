package com.kousenit.springaiexamples.functions;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Calculator {

    private final ChatClient chatClient;

    public Calculator(@Qualifier("openAiChatModel") ChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

    public String calculateLength(String expression) {
        return chatClient.prompt()
                .functions("lengthFunction")
                .user(expression)
                .call()
                .content();
    }
}
