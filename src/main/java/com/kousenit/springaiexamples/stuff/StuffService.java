package com.kousenit.springaiexamples.stuff;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

// Stuff the prompt with Markdown document from Wikipedia
@Service
public class StuffService {

    @Value("classpath:docs/wikipedia-curling.md")
    private Resource curlingResource;

    private final ChatClient chatClient;

    public StuffService(OpenAiChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    public String askQuestion(String question) {
        return chatClient.prompt()
                .system(curlingResource)
                .user(question)
                .call()
                .content();
    }
}
