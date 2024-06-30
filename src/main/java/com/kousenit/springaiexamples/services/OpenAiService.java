package com.kousenit.springaiexamples.services;

import com.kousenit.springaiexamples.output.ActorsFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class OpenAiService {
    private final ChatClient chatClient;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemResource;

    @Autowired
    public OpenAiService(OpenAiChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    public ActorsFilms getActorsFilms(String actor) {
        String userMessage =
                """
                Generate the filmography for the actor {actor}.
                {format}
                """;

        return chatClient.prompt()
                .user(userSpec -> userSpec.text(userMessage)
                        .param("actor", actor)
                        .param("format", "json"))
                .call()
                .entity(ActorsFilms.class);
    }


    public String chatWithPromptTemplate(String typeOfJoke, String topic) {
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text("Tell me a {adjective} joke about {topic}")
                        .param("adjective", typeOfJoke)
                        .param("topic", topic))
                .call()
                .content();
    }


    public String chat(String message) {
        return chatClient.prompt()
                .system(systemSpec -> systemSpec.text(systemResource)
                        .param("name", "Bob")
                        .param("voice", "pirate"))
                .user(message)
                .call()
                .content();
    }

}
