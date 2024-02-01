package com.kousenit.springaiexamples.chat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.huggingface.HuggingfaceChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("This test requires a Huggingface API key and base path")
public class HuggingfaceChatClientTest {
//    private final HuggingfaceChatClient client = new HuggingfaceChatClient(
//            System.getenv("HUGGINGFACE_API_KEY"), "");

    @Autowired
    private HuggingfaceChatClient client;

    @Test
    void testChatClient() {
        String mistral7bInstruct = """
        [INST] You are a helpful code assistant. Your task is to generate a valid JSON object based on the given information:
        name: John
        lastname: Smith
        address: #1 Samuel St.
        Just generate the JSON object without explanations:
        [/INST]""";
        Prompt prompt = new Prompt(mistral7bInstruct);
        ChatResponse response = client.call(prompt);
        System.out.println(response.getResult().getOutput());
    }
}
