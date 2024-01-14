package com.kousenit.springaiexamples.helloworld;

import com.kousenit.springaiexamples.Completion;
import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleAiController {

    private final ChatClient client;

    public SimpleAiController(@Qualifier("ollamaChatClient") ChatClient client) {
        this.client = client;
    }

    @GetMapping("/ai/simple")
    public Completion completion(@RequestParam(defaultValue = "Tell me a joke") String message) {
        return new Completion(client.generate(message));
    }
}
