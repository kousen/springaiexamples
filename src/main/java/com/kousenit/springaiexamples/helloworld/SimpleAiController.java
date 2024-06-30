package com.kousenit.springaiexamples.helloworld;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class SimpleAiController {

    private final ChatClient chatClient;

    @Autowired
    public SimpleAiController(@Qualifier("openAiChatModel") ChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    @GetMapping("/ai/generate")
    public String generate(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    @GetMapping("/ai/generateStream")
    public Flux<String> generateStream(
            @RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}
