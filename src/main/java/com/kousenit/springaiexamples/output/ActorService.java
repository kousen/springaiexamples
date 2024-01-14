package com.kousenit.springaiexamples.output;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ActorService {

    private final ChatClient chatClient;

    public ActorService(@Qualifier("openAiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ActorFilms getActorFilms(String actor) {
        var outputParser = new BeanOutputParser<>(ActorFilms.class);
        String format = outputParser.getFormat();
        String template = """
				Generate the filmography for the actor {actor}.
				{format}
				""";
        PromptTemplate promptTemplate =
                new PromptTemplate(template, Map.of("actor", actor, "format", format));
        Prompt prompt = new Prompt(promptTemplate.createMessage());
        Generation generation = chatClient.generate(prompt).getGeneration();
        System.out.println(generation.getContent());
        return outputParser.parse(generation.getContent());
    }
}
