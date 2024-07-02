package com.kousenit.springaiexamples.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.List;

@Configuration
public class SimpleVectorStoreConfig {
    @Value("file:src/main/resources/data/bikes.json")
    private Resource bikesJsonResource;

    @Value("${app.vectorstore.path:src/main/resources/data/bikesVectorStore.json}")
    private String bikesVectorStore;

    @Bean
    public SimpleVectorStore persist(@Autowired EmbeddingModel embeddingModel) {
        var vectorStore = new SimpleVectorStore(embeddingModel);
        var vectorStoreFile = new File(bikesVectorStore);

        if (vectorStoreFile.exists()) {
            vectorStore.load(vectorStoreFile);
        } else {
            var jsonReader = new JsonReader(bikesJsonResource,
                    "price", "name", "shortDescription", "description", "tags");
            List<Document> documents = jsonReader.get();

            TextSplitter textSplitter = new TokenTextSplitter();
            documents = textSplitter.apply(documents);

            vectorStore.add(documents);
            vectorStore.save(vectorStoreFile);
        }
        return vectorStore;
    }

}
