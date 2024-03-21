package com.kousenit.springaiexamples.output;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ActorService {

    private final ChatClient chatClient;

    public ActorService(@Qualifier("openAiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ActorsFilms getActorFilms(String actor) {
        var outputParser = new BeanOutputParser<>(ActorsFilms.class);
        String format = outputParser.getFormat();
        String template = """
				Generate the filmography for the actor {actor}. In your output,
				please do NOT include the backticks and json expression, as in
				```json (and the corresponding close backticks). Just include
				{format}
				""";
        PromptTemplate promptTemplate =
                new PromptTemplate(template,
                        Map.of("actor", actor, "format", format));
        Prompt prompt = new Prompt(promptTemplate.createMessage());
        String content =
                chatClient.call(prompt)
                        .getResult()
                        .getOutput()
                        .getContent();
        System.out.println(content);
        return outputParser.parse(content);
    }
}
