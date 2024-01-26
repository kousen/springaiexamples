package com.kousenit.springaiexamples.images;

import com.kousenit.springaiexamples.json.ImageGenRequest;
import org.springframework.ai.image.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageClient imageClient;

    public ImageService(@Autowired ImageClient imageClient) {
        this.imageClient = imageClient;
    }

    public ImageResponse generateImage(ImageGenRequest request) {
        ImageOptions options = ImageOptionsBuilder.builder()
                .withModel("dall-e-3")
                .build();
        ImagePrompt prompt = new ImagePrompt(request.prompt(), options);
        return imageClient.call(prompt);
    }
}
