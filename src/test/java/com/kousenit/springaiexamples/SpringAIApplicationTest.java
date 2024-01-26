package com.kousenit.springaiexamples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SpringAIApplicationTest {
    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context);
        Arrays.stream(context.getBeanDefinitionNames())
                .sorted()
                .forEach(System.out::println);
    }

}