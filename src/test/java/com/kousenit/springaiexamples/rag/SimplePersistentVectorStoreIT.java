package com.kousenit.springaiexamples.rag;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.CleanupMode;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// From spring-ai-examples project
@SpringBootTest
public class SimplePersistentVectorStoreIT {

	@Value("file:src/main/resources/data/bikes.json")
	private Resource bikesJsonResource;

	@Autowired @Qualifier("openAiEmbeddingModel")
	private EmbeddingModel embeddingModel;

	@Test
	void persist(@TempDir(cleanup = CleanupMode.ON_SUCCESS) Path workingDir) {
		var jsonReader = new JsonReader(bikesJsonResource,
				"price", "name", "shortDescription", "description", "tags");
		List<Document> documents = jsonReader.get();
		var vectorStore = new SimpleVectorStore(this.embeddingModel);
		vectorStore.add(documents);

		File tempFile = new File(workingDir.toFile(), "temp.txt");
		vectorStore.save(tempFile);
		assertThat(tempFile).isNotEmpty();
		assertThat(tempFile).content().contains("Velo 99 XR1 AXS");}
}
