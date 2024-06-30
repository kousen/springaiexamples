package com.kousenit.springaiexamples.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.http.HttpHeaders;

import java.util.Map;
import java.util.function.Function;

public class ExchangeRateFunction implements
        Function<ExchangeRateFunction.Request, ExchangeRateFunction.Response> {

    public record Request(double fromAmount, String from, String to) {}
    public record Response(double toAmount) {}

    public record OpenExchangeRatesResponse(
            String disclaimer,
            String license,
            long timestamp,
            String base,
            Map<String, Double> rates
    ) {}

    private static final String API_KEY = System.getenv("OPENEXCHANGERATES_API_KEY");

    private final Map<String, Double> rates;
    private final Logger logger = LoggerFactory.getLogger(ExchangeRateFunction.class);

    public ExchangeRateFunction() {
        this.rates = getLatestRates().rates();
    }

    @Override
    public Response apply(Request request) {
        logger.info("Converting {} rate from {} to {}", request.fromAmount, request.from, request.to);
        return new Response( request.fromAmount *
                rates.get(request.to) / rates.get(request.from));
    }

    // Retrieve the current exchange rates
    private OpenExchangeRatesResponse getLatestRates() {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://openexchangerates.org")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Token " + API_KEY)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return restClient.get()
                .uri("/api/latest.json")
                .retrieve()
                .body(OpenExchangeRatesResponse.class);
    }
}
