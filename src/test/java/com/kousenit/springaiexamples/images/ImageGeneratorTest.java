package com.kousenit.springaiexamples.images;

import org.junit.jupiter.api.Test;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImageGeneratorTest {
    @Autowired
    private ImageGenerator imageGenerator;

    @Test
    void generateImage() {
        ImageResponse response = imageGenerator.generate(
                """
                        A warrior cat rides a dragon
                        into battle against a horde of
                        zombies in a post-apocalyptic world.
                        """);
        System.out.println(response.getResult().getOutput());
        System.out.println(response);
    }
}