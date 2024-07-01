package com.kousenit.springaiexamples.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@Description("Take the square root of a number")
public class SqrtService implements Function<SqrtService.SqrtRequest, SqrtService.SqrtResponse> {

    public record SqrtRequest(double value) {}
    public record SqrtResponse(double squareRoot) {}

    private final Logger logger = LoggerFactory.getLogger(SqrtService.class);

    @Override
    public SqrtResponse apply(SqrtRequest request) {
        logger.info("Calculating square root of {}", request.value);
        return new SqrtResponse(Math.sqrt(request.value));
    }
}
