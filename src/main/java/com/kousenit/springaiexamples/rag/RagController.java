package com.kousenit.springaiexamples.rag;

import org.springframework.ai.chat.Generation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RagController {

    private final RagService service;

    public RagController(RagService service) {
        this.service = service;
    }

    @GetMapping("/ai/rag")
    public Generation generate(
            @RequestParam(defaultValue = "What bike is good for city commuting?") String message) {
        return service.retrieve(message);
    }
}
