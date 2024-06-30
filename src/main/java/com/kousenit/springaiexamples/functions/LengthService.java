package com.kousenit.springaiexamples.functions;

import java.util.function.Function;

public class LengthService implements Function<LengthService.LengthRequest, LengthService.LengthResponse> {

    public record LengthRequest(String expression) {}
    public record LengthResponse(int length) {}

    @Override
    public LengthResponse apply(LengthRequest request) {
        return new LengthResponse(request.expression.length());
    }
}
