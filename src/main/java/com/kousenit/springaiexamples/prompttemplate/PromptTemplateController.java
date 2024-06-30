package com.kousenit.springaiexamples.prompttemplate;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PromptTemplateController {

    private final ChatClient chatClient;

    @Value("classpath:/prompts/joke-prompt.st")
    private Resource jokeResource;

    public PromptTemplateController(@Qualifier("openAiChatModel") ChatModel chatModel) {
        chatClient = ChatClient.create(chatModel);
    }

    @GetMapping("/ai/prompt")
    public String completion(
            @RequestParam(defaultValue = "funny") String adjective,
            @RequestParam(defaultValue = "cows") String topic) {

        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(jokeResource)
                        .param("adjective", adjective)
                        .param("topic", topic))
                .call()
                .content();
    }
}
