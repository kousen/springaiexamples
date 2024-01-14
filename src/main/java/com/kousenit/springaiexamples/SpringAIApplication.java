package com.kousenit.springaiexamples;

import com.kousenit.springaiexamples.rag.RagService;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAIApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAIApplication.class, args);
    }

    @Bean
    public RagService ragService(@Qualifier("openAiChatClient") ChatClient chatClient,
                                 EmbeddingClient embeddingClient) {
        return new RagService(chatClient, embeddingClient);
    }

}
