package com.kousenit.springaiexamples.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.IntStream;

@Component
@Description("Sum a list of integers")
public class SumService implements Function<SumService.SumRequest, SumService.SumResponse> {

    public record SumRequest(int... ints) {}
    public record SumResponse(int total) {}

    private final Logger logger = LoggerFactory.getLogger(LengthService.class);

    @Override
    public SumResponse apply(SumRequest request) {
        logger.info("Calculating sum of integers: {}", request.ints());
        return new SumResponse(IntStream.of(request.ints()).sum());
    }
}
