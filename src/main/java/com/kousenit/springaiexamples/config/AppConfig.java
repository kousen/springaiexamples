package com.kousenit.springaiexamples.config;

import com.kousenit.springaiexamples.functions.LengthService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.function.Function;

@Configuration
public class AppConfig {
    @Value("classpath:/pdfs/Model_3_Owners_Manual.pdf")
    Resource documentResource;

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

//    @Bean
//    @Description("Get the exchange rate between two currencies")
//    public Function<ExchangeRateFunction.Request, ExchangeRateFunction.Response> exchangeRateFunction() {
//        return new ExchangeRateFunction();
//    }

//    @Bean
//    @Primary
//    public VectorStore chromaVectorStore(OpenAiEmbeddingModel openAiEmbeddingModel,
//                                         ChromaApi chromaApi) {
//        return new ChromaVectorStore(openAiEmbeddingModel, chromaApi, true);
//    }

    @Bean
    ApplicationRunner go(VectorStore vectorStore) {
        return args -> {
            TikaDocumentReader reader = new TikaDocumentReader(documentResource);
            List<Document> documents = reader.get();
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> splitDocuments = textSplitter.apply(documents);
            System.out.println("Adding " + splitDocuments.size() + " documents to vector store");
            vectorStore.add(splitDocuments);
        };
    }


}
