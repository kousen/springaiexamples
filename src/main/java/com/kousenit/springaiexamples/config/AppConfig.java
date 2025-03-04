package com.kousenit.springaiexamples.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class AppConfig {
    @Value("classpath:/pdfs/Model_3_Owners_Manual.pdf")
    Resource documentResource;

    // Enable this bean to load the documents from the PDF file
    @Bean @Profile("rag")
    ApplicationRunner go(VectorStore vectorStore) {
        return args -> {
            System.out.println("Loading documents from PDF file into " +
                               vectorStore.getClass().getSimpleName());
            TikaDocumentReader reader = new TikaDocumentReader(documentResource);
            List<Document> documents = reader.get();
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> splitDocuments = textSplitter.apply(documents);
            System.out.println("Adding " + splitDocuments.size() + " documents to vector store");
            vectorStore.add(splitDocuments);
        };
    }

    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
//        var embeddingModel = new OpenAiEmbeddingModel(
//                OpenAiApi.builder()
//                        .apiKey(System.getenv("OPENAI_API_KEY"))
//                        .build());
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    RestClient.Builder restClientBuilder() {
        var factory = new JdkClientHttpRequestFactory();
        factory.setReadTimeout(30000); // 30 seconds for image generation
        return RestClient.builder().requestFactory(factory);
    }

}
