package com.kousenit.springaiexamples.images;

import com.kousenit.springaiexamples.json.ImageGenRequest;
import org.junit.jupiter.api.Test;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Test
    void generateImage() {
        ImageGenRequest request = new ImageGenRequest(
                """
                        A smiling robot leaping into the air
                        on springs in joy, happy that it
                        accomplished a difficult task successfully.
                        """);
        ImageResponse response = imageService.generateImage(request);
        assertNotNull(response);
        System.out.println(response);
    }

}