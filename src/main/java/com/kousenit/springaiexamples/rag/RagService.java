package com.kousenit.springaiexamples.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.SystemPromptTemplate;
import org.springframework.ai.prompt.messages.Message;
import org.springframework.ai.prompt.messages.UserMessage;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RagService {
    private static final Logger logger = LoggerFactory.getLogger(RagService.class);

    @Value("classpath:/data/bikes.json")
    private Resource bikesResource;

    @Value("classpath:/prompts/system-qa.st")
    private Resource systemBikePrompt;

    private final ChatClient aiClient;
    private final EmbeddingClient embeddingClient;

    @Autowired
    public RagService(@Qualifier("openAiChatClient") ChatClient aiClient, EmbeddingClient embeddingClient) {
        this.aiClient = aiClient;
        this.embeddingClient = embeddingClient;
    }

    public Generation retrieve(String message) {
        SimpleVectorStore vectorStore = new SimpleVectorStore(embeddingClient);
        File bikeVectorStore = new File("src/main/resources/data/bikeVectorStore.json");
        if (bikeVectorStore.exists()) {
            vectorStore.load(bikeVectorStore);
        } else {
            // Step 1 - Load JSON document as Documents
            logger.info("Loading JSON as Documents");
            JsonReader jsonReader = new JsonReader(bikesResource,
                    "name", "price", "shortDescription", "description");
            List<Document> documents = jsonReader.get();
            logger.info("Loading JSON as Documents");

            // Step 2 - Create embeddings and save to vector store
            logger.info("Creating Embeddings...");
            vectorStore.add(documents);
            logger.info("Embeddings created.");
            vectorStore.save(bikeVectorStore);
        }

        // Step 3 retrieve related documents to query
        logger.info("Retrieving relevant documents");
        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.query(message).withTopK(2));
        logger.info(String.format("Found %s relevant documents.", similarDocuments.size()));

        // Step 4 Embed documents into SystemMessage with the `system-qa.st` prompt template
        Message systemMessage = getSystemMessage(similarDocuments);
        UserMessage userMessage = new UserMessage(message);

        // Step 4 - Ask the AI model

        logger.info("Asking AI model to reply to question.");
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        logger.info(prompt.toString());
        ChatResponse response = aiClient.generate(prompt);
        logger.info("AI responded.");
        logger.info(response.getGeneration().toString());
        return response.getGeneration();
    }

    private Message getSystemMessage(List<Document> similarDocuments) {

        String documents = similarDocuments.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemBikePrompt);
        return systemPromptTemplate.createMessage(Map.of("documents", documents));

    }
}
