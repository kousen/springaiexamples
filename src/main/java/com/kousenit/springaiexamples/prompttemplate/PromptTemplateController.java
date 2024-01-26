package com.kousenit.springaiexamples.prompttemplate;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PromptTemplateController {

    private final ChatClient chatClient;

    @Value("classpath:/prompts/joke-prompt.st")
    private Resource jokeResource;

    public PromptTemplateController(@Qualifier("openAiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai/prompt")
    public Generation completion(@RequestParam(defaultValue = "funny") String adjective,
                                 @RequestParam(defaultValue = "cows") String topic) {
        PromptTemplate promptTemplate = new PromptTemplate(jokeResource);
        Prompt prompt = promptTemplate.create(
                Map.of("adjective", adjective, "topic", topic));
        return chatClient.call(prompt).getResult();
    }
}
