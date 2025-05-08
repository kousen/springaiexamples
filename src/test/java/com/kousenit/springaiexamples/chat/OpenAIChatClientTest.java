package com.kousenit.springaiexamples.chat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")
@SpringBootTest
public class OpenAIChatClientTest {

    @Autowired
    private OpenAiChatModel chatModel;

    @Value("classpath:/skynet.jpg")
    private Resource skynetImage;

    @Test
    void testChatClient() {
        String question = "Why is the sky blue?";
        String answer = chatModel.call(question);
        System.out.println(answer);

        var evaluator = new RelevancyEvaluator(ChatClient.builder(chatModel));
        var request = new EvaluationRequest(question, List.of(), answer);
        EvaluationResponse response = evaluator.evaluate(request);
        System.out.println(response);
    }

    @Test
    void testChatClientWithPrompt() {
        String question = "Why is the sky blue?";
        ChatResponse response = ChatClient.create(chatModel)
                .prompt()
                .user(u -> u.text(question))
                .call()
                .chatResponse();
        Assertions.assertNotNull(response);
        ChatResponseMetadata metadata = response.getMetadata();
        System.out.println("Model: " + metadata.getModel());
        System.out.println("Usage: " + metadata.getUsage());
        System.out.println(response.getResult());
    }

    @Test
    void testChatClientWithStreamingPrompt() {
        String question = "Why is the sky blue?";
        Flux<String> content = ChatClient.create(chatModel)
                .prompt()
                .user(u -> u.text(question))
                .stream()
                .content();
        content.doOnNext(System.out::println)
                .blockLast();
    }

    @Test
    void listOutputConverterString() {
        List<String> collection = ChatClient.create(chatModel)
                .prompt()
                .user(u -> u.text("List five {subject}")
                        .param("subject", "ice cream flavors"))
                .call()
                .entity(new ParameterizedTypeReference<>() {});

        assertThat(collection).hasSize(5);
        collection.forEach(System.out::println);
    }

    @Test
    void visionTest() {
        String response = ChatClient.create(chatModel)
                .prompt()
                .user(u -> u.text("""
                                My boss wants me to embed
                                an AI model into this robot,
                                which my company (identified
                                by the logo in the picture)
                                is planning to build.
                                
                                What could go wrong?
                                """)
                        .media(MimeTypeUtils.IMAGE_JPEG, skynetImage))
                .call()
                .content();
        assertThat(response).containsIgnoringCase("skynet");
        System.out.println(response);
    }
}
