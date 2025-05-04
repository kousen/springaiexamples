package com.kousenit.springaiexamples.output;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ActorService {

    private final ChatClient chatClient;

    public ActorService(@Qualifier("openAiChatModel") ChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    public ActorsFilms getActorFilms(String actor) {
        String template = """
				Generate the filmography for the actor {actor}.
				""";

        ActorsFilms actorsFilms = chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(template)
                        .param("actor", actor))
                .call()
                .entity(ActorsFilms.class);
        System.out.println("Films for " + actor + ":");
        assert actorsFilms != null;
        actorsFilms.movies().forEach(System.out::println);
        return actorsFilms;
    }
}
