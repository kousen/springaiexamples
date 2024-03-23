package com.kousenit.springaiexamples.images;

import org.springframework.ai.image.ImageClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAiImageService {

    private final ImageClient imageClient;

    @Autowired
    public OpenAiImageService(ImageClient imageClient) {
        this.imageClient = imageClient;
    }

    public ImageResponse getImageResponse(String description) {
        // All defaults
//        ImagePrompt prompt = new ImagePrompt(description);
//        return imageClient.call(prompt);

        // Return the image as a base64-encoded JSON string
        OpenAiImageOptions options = OpenAiImageOptions.builder()
                .withResponseFormat("b64_json")
                .build();
        return imageClient.call(new ImagePrompt(description, options));
    }
}
