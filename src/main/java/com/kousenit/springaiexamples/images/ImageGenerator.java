package com.kousenit.springaiexamples.images;


import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageGenerator {
    private final OpenAiImageModel imageModel;

    public ImageGenerator(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public ImageResponse generate(String prompt) {
        var options = OpenAiImageOptions.builder()
                .withQuality("hd")
                .withHeight(1024)
                .withWidth(1024)
                .build();

        var imagePrompt = new ImagePrompt(prompt, options);
        return imageModel.call(imagePrompt);
    }
}
