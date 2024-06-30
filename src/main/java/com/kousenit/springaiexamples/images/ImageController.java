package com.kousenit.springaiexamples.images;

import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    private final ImageGenerator imageGenerator;

    @Autowired
    public ImageController(ImageGenerator imageGenerator) {
        this.imageGenerator = imageGenerator;
    }

    @PostMapping("/image")
    public ImageResponse generateImage(@RequestBody String prompt) {
        return imageGenerator.generate(prompt);
    }
}
