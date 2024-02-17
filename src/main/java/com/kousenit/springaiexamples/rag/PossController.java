package com.kousenit.springaiexamples.rag;

import org.springframework.ai.chat.Generation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PossController {

    private final PossService service;

    public PossController(PossService service) {
        this.service = service;
    }

    @GetMapping("/ai/poss")
    public Generation generate(
            @RequestParam(defaultValue =
                    "Generate several multiple-choice questions about this chapter") String message) {
        return service.retrieve(message);
    }
}
