package com.kousenit.springaiexamples.services;

import com.kousenit.springaiexamples.output.ActorsFilms;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class OpenAiService {
    private final ChatClient chatClient;

    @Value("classpath:/prompts/system-message.st")
    private Resource systemResource;

    @Autowired
    public OpenAiService(OpenAiChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel)
                // Add chat memory:
                // .defaultAdvisors(new PromptChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    public ActorsFilms getActorsFilms(String actor) {
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text("Generate the filmography for {actor}.")
                        .param("actor", actor))
                .call()
                .entity(ActorsFilms.class);
    }

    public List<String> getFilmsForActor(String actor) {
        return chatClient.prompt()
                .user("Generate the filmography for " + actor)
                .call()
                .entity(new ParameterizedTypeReference<>() {});
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

    // Can't do "entity" for async, so this is more of a demo
    public Flux<String> getActorFilmsListAsync(String... actors) {
        String allActors = String.join(", ", actors);
        return chatClient.prompt()
                .user("Generate the filmography for " + allActors)
                .stream()
                .content();
    }

}
