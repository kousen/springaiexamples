package com.kousenit.springaiexamples.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

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

//    @Bean
//    @Description("Get the length of a string")
//    public Function<LengthService.LengthRequest, LengthService.LengthResponse> lengthFunction() {
//        return new LengthService();
//    }

//    @Bean
//    @Description("Get the exchange rate between two currencies")
//    public Function<ExchangeRateFunction.Request, ExchangeRateFunction.Response> exchangeRateFunction() {
//        return new ExchangeRateFunction();
//    }

    // Uncomment this bean to load the documents from the PDF file
//    @Bean
//    ApplicationRunner go(VectorStore vectorStore) {
//        return args -> {
//            TikaDocumentReader reader = new TikaDocumentReader(documentResource);
//            List<Document> documents = reader.get();
//            TextSplitter textSplitter = new TokenTextSplitter();
//            List<Document> splitDocuments = textSplitter.apply(documents);
//            System.out.println("Adding " + splitDocuments.size() + " documents to vector store");
//            vectorStore.add(splitDocuments);
//        };
//    }

    @Bean
    RestClient.Builder restClientBuilder() {
        var factory = new JdkClientHttpRequestFactory();
        factory.setReadTimeout(30000); // 30 seconds for image generation
        return RestClient.builder().requestFactory(factory);
    }

}
