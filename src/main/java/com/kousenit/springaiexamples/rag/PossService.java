package com.kousenit.springaiexamples.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PossService {
    private static final Logger logger = LoggerFactory.getLogger(RagService.class);

    @Value("classpath:/data/chapter3_poss.txt")
    private Resource chapterResource;

    @Value("classpath:/prompts/quiz-prompt.st")
    private Resource quizPrompt;

    private final ChatClient aiClient;
    private final EmbeddingClient embeddingClient;

    @Autowired
    public PossService(@Qualifier("openAiChatClient") ChatClient aiClient,
                       EmbeddingClient embeddingClient) {
        this.aiClient = aiClient;
        this.embeddingClient = embeddingClient;
    }

    public Generation retrieve(String message) {
        SimpleVectorStore vectorStore = new SimpleVectorStore(embeddingClient);
        File possVectorStore = new File("src/main/resources/data/possVectorStore.json");
        if (possVectorStore.exists()) {
            vectorStore.load(possVectorStore);
        } else {
            DocumentReader documentReader = new TextReader(chapterResource);
            List<Document> documents = documentReader.get();
            TextSplitter splitter = new TokenTextSplitter();
            List<Document> splitDocuments = splitter.apply(documents);
            logger.info("Creating Embeddings...");
            vectorStore.add(splitDocuments);
            logger.info("Embeddings created.");
            vectorStore.save(possVectorStore);
        }

        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.query(message).withTopK(4));

        Message systemMessage = getSystemMessage(similarDocuments, message);
        UserMessage userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = aiClient.call(prompt);
        logger.info(response.getResult().toString());
        return response.getResult();
    }

    private Message getSystemMessage(List<Document> similarDocuments, String message) {
        String documents = similarDocuments.stream()
                .map(Document::getContent)
                .collect(Collectors.joining("\n"));
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(quizPrompt);
        return systemPromptTemplate.createMessage(
                Map.of("documents", documents, "topic", message));

    }
}
