package com.kousenit.springaiexamples.config;

import com.kousenit.springaiexamples.rag.RagService;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chroma.ChromaApi;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorsore.ChromaVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RagService ragService(@Qualifier("openAiChatClient") ChatClient chatClient,
                                 VectorStore vectorStore) {
        return new RagService(chatClient, vectorStore);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ChromaApi chromaApi() {
        return new ChromaApi("http://localhost:8000", restTemplate());
    }

    @Bean
    public VectorStore chromaVectorStore(EmbeddingClient embeddingClient, ChromaApi chromaApi) {
        return new ChromaVectorStore(embeddingClient, chromaApi, "BikeCollection");
    }

}
