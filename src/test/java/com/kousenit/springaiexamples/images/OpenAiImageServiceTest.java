package com.kousenit.springaiexamples.images;

import org.junit.jupiter.api.Test;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OpenAiImageServiceTest {
    @Autowired
    private OpenAiImageService service;

    @Test
    void generateCatImage() {
        ImageResponse response = service.getImageResponse("A picture of a cat");
        byte[] bytes = Base64.getDecoder()
                .decode(response.getResult()
                        .getOutput()
                        .getB64Json());
        try {
            Files.write(Paths.get("src/main/resources/output.png"), bytes);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    void generateRobotImage() {
        String request = """
                        A smiling robot leaping into the air
                        on springs in joy, happy that it
                        accomplished a difficult task successfully.
                        """;
        ImageResponse response = service.getImageResponse(request);
        assertNotNull(response);
        System.out.println(response);
    }

}