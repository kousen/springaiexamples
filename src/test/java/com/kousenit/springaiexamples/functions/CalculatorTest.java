package com.kousenit.springaiexamples.functions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CalculatorTest {

    @Autowired
    private Calculator calculator;

    @Test
    void testCalculateLength() {
        String testString = "The quick brown fox jumped over the lazy dog";
        String result = calculator.calculateLength(testString);

        System.out.println(result);
        System.out.println(testString.length());
    }
}