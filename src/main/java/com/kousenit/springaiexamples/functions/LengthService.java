package com.kousenit.springaiexamples.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class LengthService implements Function<LengthService.LengthRequest, LengthService.LengthResponse> {

    public record LengthRequest(String expression) {}
    public record LengthResponse(int length) {}

    private final Logger logger = LoggerFactory.getLogger(LengthService.class);

    @Override
    public LengthResponse apply(LengthRequest request) {
        logger.info("Calculating length of expression: {}", request.expression);
        return new LengthResponse(request.expression.length());
    }
}
