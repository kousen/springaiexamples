package com.kousenit.springaiexamples.role;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    private final ChatClient chatClient;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemResource;

    @Autowired
    public RoleController(@Qualifier("openAiChatModel") ChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

    @GetMapping("/ai/roles")
    public List<Generation> generate(
            @RequestParam(value = "message", defaultValue = """
                Tell me about three famous pirates from the
                Golden Age of Piracy and why they did.  Write
                at least a sentence for each pirate.""") String message,
            @RequestParam(value = "name", defaultValue = "Bob") String name,
            @RequestParam(value = "voice", defaultValue = "pirate") String voice) {

        return chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(systemResource)
                        .param("name", name)
                        .param("voice", voice))
                .user(message)
                .call()
                .chatResponse().getResults();
    }
}