package com.kousenit.springaiexamples.services;

import com.kousenit.springaiexamples.output.ActorsFilms;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {
    private final OpenAiChatClient chatClient;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemResource;

    @Value("classpath:/prompts/person-template.st")
    private Resource personTemplateResource;

    @Autowired
    public OpenAiService(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // public record Person(String firstName, String lastName, LocalDate dob) { }

    public String parsePerson(String input) {
        SystemMessage systemMessage =
                new SystemMessage(personTemplateResource);
        UserMessage userMessage = new UserMessage(input);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatClient.call(prompt);
        return response.getResults().get(0).getOutput().getContent();
    }

    public ActorsFilms getActorsFilms(String actor) {
        var outputParser = new BeanOutputParser<>(ActorsFilms.class);

        String userMessage =
                """
                Generate the filmography for the actor {actor}.
                {format}
                """;

        PromptTemplate promptTemplate =
                new PromptTemplate(userMessage,
                        Map.of("actor", actor, "format", outputParser.getFormat() ));
        Prompt prompt = promptTemplate.create();
        Generation generation = chatClient.call(prompt).getResult();
        return outputParser.parse(generation.getOutput().getContent());
    }


    public String chatWithPromptTemplate(String typeOfJoke, String topic) {
        PromptTemplate promptTemplate =
                new PromptTemplate("Tell me a {adjective} joke about {topic}");

        Prompt prompt = promptTemplate.create(
                Map.of("adjective", typeOfJoke, "topic", topic));

        return chatClient.call(prompt).getResult().getOutput().getContent();
    }


    public String chat(String message) {
        UserMessage userMessage = new UserMessage(message);

        // Moved text to template file system-message.st
//        String systemText = """
//              You are a helpful AI assistant that helps people find information.
//              Your name is {name}
//              You should reply to the user's request with your name and
//              also in the style of a {voice}.
//              """;

        SystemPromptTemplate systemPromptTemplate =
                new SystemPromptTemplate(systemResource);
        Message systemMessage = systemPromptTemplate.createMessage(
                Map.of("name", "Bob", "voice", "pirate"));

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse response = chatClient.call(prompt);
        return response.getResults().get(0).getOutput().getContent();
    }

}
