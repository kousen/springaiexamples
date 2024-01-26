package com.kousenit.springaiexamples.config;

import com.kousenit.springaiexamples.rag.RagService;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.image.ImageClient;
import org.springframework.ai.openai.OpenAiImageClient;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public RagService ragService(@Qualifier("openAiChatClient") ChatClient chatClient,
                                 EmbeddingClient embeddingClient) {
        return new RagService(chatClient, embeddingClient);
    }

    @Bean
    public RestClient restClient(@Value("${spring.ai.openai.chat.base-url}") String baseUrl,
                                 @Value("${spring.ai.openai.chat.api-key}") String apiKey) {
        return RestClient.builder()
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public ImageClient imageClient(@Value("${spring.ai.openai.chat.api-key}") String apiKey) {
        return new OpenAiImageClient(new OpenAiImageApi(apiKey));
    }

//    @Bean
//    public SimpleVectorStore vectorStore(EmbeddingClient embeddingClient) {
//        SimpleVectorStore vectorStore = new SimpleVectorStore(embeddingClient);
//        List<Document> documents = new TikaDocumentReader(url).get();
//        TextSplitter textSplitter = new TokenTextSplitter();
//        List<Document> splitDocuments = textSplitter.apply(documents);
//        vectorStore.add(splitDocuments);
//        return vectorStore;
//    }

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//
//    @Bean
//    public ChromaApi chromaApi() {
//        return new ChromaApi("http://localhost:8000", restTemplate());
//    }
//
//    @Bean
//    public VectorStore chromaVectorStore(EmbeddingClient embeddingClient, ChromaApi chromaApi) {
//        return new ChromaVectorStore(embeddingClient, chromaApi);
//    }

}
