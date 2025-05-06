package com.kousenit.springaiexamples.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class Tools {
    private final Logger logger = LoggerFactory.getLogger(Tools.class);

    @Tool(description = "Get the length of a string")
    public int stringLength(String expression) {
        logger.info("Calculating length of expression: {}", expression);
        return expression.length();
    }

    @Tool(description = "Sum a series of integers")
    public int sumIntegers(int... ints) {
        logger.info("Calculating sum of integers: {}", ints);
        return java.util.stream.IntStream.of(ints).sum();
    }

    @Tool(description = "Take the square root of a number")
    public double sqrt(double value) {
        logger.info("Calculating square root of {}", value);
        return Math.sqrt(value);
    }
}
