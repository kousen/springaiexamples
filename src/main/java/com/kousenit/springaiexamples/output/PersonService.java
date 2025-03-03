package com.kousenit.springaiexamples.output;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class PersonService {

    public record Person(String firstName, String lastName, LocalDate dob) {}

    private final ChatModel chatModel;

    public PersonService(@Qualifier("openAiChatModel") ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public Person retrievePerson(String text) {
        var parser = new BeanOutputConverter<>(Person.class);

        String template = """
                Extract a Person instance from the given {text} using the {format}
                Use ISO-8601 standard date format for the date of birth.
                Do NOT include the JSON delimiters ```json or ``` in the response.
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template,
                Map.of("text", text, "format", parser.getFormat()));
        String content = chatModel.call(promptTemplate.create())
                .getResult().getOutput().getText();
        System.out.println(content);
        return parser.convert(content);
    }
}
