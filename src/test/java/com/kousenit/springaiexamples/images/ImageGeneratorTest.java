package com.kousenit.springaiexamples.images;

import org.junit.jupiter.api.Test;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageGeneratorTest {
    @Autowired
    private ImageGenerator imageGenerator;

    @Test  // Always times out. Don't know how to fix that yet
    void generateImage() {
        ImageResponse response = imageGenerator.generate(
                "A beautiful sunset over the ocean");
        System.out.println(response.getResult().getOutput());
        System.out.println(response);
    }
}