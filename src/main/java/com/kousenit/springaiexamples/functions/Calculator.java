package com.kousenit.springaiexamples.functions;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class Calculator {

    private final ChatClient chatClient;

    public Calculator(@Qualifier("openAiChatModel") ChatModel chatModel) {
        this.chatClient = ChatClient.create(chatModel);
    }

    public String calculateLength(String expression) {
        String message = """
                Calculate the length of this expression:
                "%s"
                """.formatted(expression);
        return chatClient.prompt()
                .tools(new Tools())
                .user(message)
                .call()
                .content();
    }

    public String calculateSum(int... ints) {
        String message = """
                Calculate the sum of these integers:
                %s
                """.formatted(Arrays.toString(ints));
        return chatClient.prompt()
                .tools(new Tools())
                .user(message)
                .call()
                .content();
    }

    public String calculateSqrt(double value) {
        String message = """
                Calculate the square root of this number:
                %s
                """.formatted(value);
        return chatClient.prompt()
                .tools(new Tools())
                .user(message)
                .call()
                .content();
    }

    public String sqrtSumLengths(String sentence) {
        String message = """
                Calculate the square root of the sum
                of the lengths of the words in this sentence:
                "%s"
                """.formatted(sentence);
        return chatClient.prompt()
                .tools(new Tools())
                .user(message)
                .call()
                .content();
    }
}
