package com.kousenit.springaiexamples.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    private final SimpleVectorStore vectorStore;
    private final ChatClient chatClient;

    @Value("classpath:/prompts/rag-prompt.st")
    private Resource ragPromptResource;

    public RagService(SimpleVectorStore vectorStore, ChatModel chatModel) {
        this.vectorStore = vectorStore;
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    public String queryVectorStore(String question) {
        // Search for similar documents in the vector database
        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.defaults()
                        .withQuery(question)
                        .withSimilarityThreshold(0.5)
                        .withTopK(10));

        // Convert the found documents to a list of strings,
        // joined together so they can be added to the prompt
        List<String> contentList = similarDocuments.stream()
                .map(Document::getContent)
                .toList();
        System.out.printf("Found %d similar documents.%n", contentList.size());
        String documentsAsString = String.join(System.lineSeparator(), contentList);

        // Prompt the user with the question and the found documents
        return chatClient.prompt()
                .user(userSpec -> userSpec.text(ragPromptResource)
                        .param("input", question)
                        .param("documents", documentsAsString))
                .call()
                .content();
    }
}
