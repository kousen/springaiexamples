package com.kousenit.springaiexamples.config;

import com.kousenit.springaiexamples.functions.ExchangeRateFunction;
import com.kousenit.springaiexamples.functions.LengthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.client.RestClient;

import java.util.function.Function;

@Configuration
public class AppConfig {

//    @Bean
//    public RagService ragService(@Qualifier("openAiChatClient") ChatClient chatClient,
//                                 @Qualifier("openAiEmbeddingClient") EmbeddingClient embeddingClient) {
//        return new RagService(chatClient, embeddingClient);
//    }

    @Bean
    public RestClient openAiRestClient(
            @Value("${spring.ai.openai.chat.base-url}") String baseUrl,
            @Value("${spring.ai.openai.chat.api-key}") String apiKey) {
        return RestClient.builder()
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    @Description("Get the length of a string")
    public Function<LengthService.LengthRequest, LengthService.LengthResponse> lengthFunction() {
        return new LengthService();
    }

    @Bean
    @Description("Get the exchange rate between two currencies")
    public Function<ExchangeRateFunction.Request, ExchangeRateFunction.Response> exchangeRateFunction() {
        return new ExchangeRateFunction();
    }


//    @Bean
//    public ImageClient imageClient(@Value("${spring.ai.openai.chat.api-key}") String apiKey) {
//        return new OpenAiImageClient(new OpenAiImageApi(apiKey));
//    }

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
