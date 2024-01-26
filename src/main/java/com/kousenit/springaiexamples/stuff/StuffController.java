package com.kousenit.springaiexamples.stuff;

import com.kousenit.springaiexamples.json.Completion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StuffController {
    private static final Logger logger = LoggerFactory.getLogger(StuffController.class);

    private final ChatClient chatClient;

    @Value("classpath:/docs/curling-source.txt")
    private Resource docsToStuffResource;

    @Value("classpath:/prompts/qa-prompt.st")
    private Resource qaPromptResource;

    @Autowired
    public StuffController(@Qualifier("openAiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai/stuff")
    public Completion completion(@RequestParam(defaultValue =
            "Which athletes won the mixed doubles gold medal in curling at the 2022 Winter Olympics?") String message,
                                 @RequestParam(defaultValue = "false") boolean stuffit) {
        PromptTemplate promptTemplate = new PromptTemplate(qaPromptResource);
        Map<String, Object> map = Map.ofEntries(
                Map.entry("question", message),
                Map.entry("context", (stuffit ? docsToStuffResource : "")));
        Prompt prompt = promptTemplate.create(map);
        logger.info("Prompt: {}", prompt);
        ChatResponse aiResponse = chatClient.call(prompt);
        return new Completion(aiResponse.getResult().getOutput().getContent());
    }

}