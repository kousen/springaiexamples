package com.kousenit.springaiexamples.functions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

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

    @Test
    void testCalculateSum() {
        int[] testInts = {1, 2, 3, 4, 5};
        String result = calculator.calculateSum(testInts);

        System.out.println(result);
        System.out.println(15);
    }

    @Test
    void testCalculateSqrt() {
        double testValue = 42.0;
        String result = calculator.calculateSqrt(testValue);

        System.out.println(result);
        System.out.println(Math.sqrt(testValue));
    }

    @Test
    void testSqrtSumLengths() {
        String testString = "The quick brown fox jumped over the lazy dog";
        double expected = Math.sqrt(
                Stream.of(testString.split(" "))
                        .mapToInt(String::length)
                        .sum());
        String result = calculator.sqrtSumLengths(testString);

        System.out.println("Actual: " + result);
        System.out.println("Expected: " + expected);
    }
}