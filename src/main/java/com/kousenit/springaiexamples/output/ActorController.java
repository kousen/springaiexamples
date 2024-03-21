package com.kousenit.springaiexamples.output;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActorController {
    private final ActorService service;

    public ActorController(ActorService service) {
        this.service = service;
    }

    @GetMapping("/actor")
    List<String> getActorFilms(
            @RequestParam(value = "actor", defaultValue = "Margot Robbie") String actor) {
        return service.getActorFilms(actor).movies();
    }
}
