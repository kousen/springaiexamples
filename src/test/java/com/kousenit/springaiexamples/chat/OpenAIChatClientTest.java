package com.kousenit.springaiexamples.chat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;

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
        System.out.println(chatModel.call("Why is the sky blue?"));
    }

    @Test
    void listOutputConverterString() {
        List<String> collection = ChatClient.create(chatModel)
                .prompt()
                .user(u -> u.text("List five {subject}")
                        .param("subject", "ice cream flavors"))
                .call()
                .entity(new ParameterizedTypeReference<>() {
                });

        assertThat(collection).hasSize(5);
        collection.forEach(System.out::println);
    }

    @Test
    void visionTest() {
        String response = ChatClient.create(chatModel)
                .prompt()
                .user(u -> u.text("What do you see in this image?")
                        .media(MimeTypeUtils.IMAGE_JPEG, skynetImage))
                .call()
                .content();
        assertThat(response).containsIgnoringCase("skynet");
        System.out.println(response);
    }
}
