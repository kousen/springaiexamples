package com.kousenit.springaiexamples.output;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class PersonService {

    public record Person(String firstName, String lastName, LocalDate dob) {}

    private final ChatClient chatClient;

    public PersonService(@Qualifier("openAiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public Person retrievePerson(String text) {
        BeanOutputParser<Person> parser = new BeanOutputParser<>(Person.class);

        String template = """
                Extract a Person instance from the given {text} using the {format}
                Use ISO-8601 standard date format for the date of birth.
                Do NOT include the JSON delimeters ```json or ``` in the response.
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template,
                Map.of("text", text, "format", parser.getFormat()));
        String content = chatClient.call(promptTemplate.create())
                .getResult().getOutput().getContent();
        System.out.println(content);
        return parser.parse(content);
    }
}
