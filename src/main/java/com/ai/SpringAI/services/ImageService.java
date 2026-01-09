package com.ai.SpringAI.services;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private  final OpenAiImageModel imageModel;

    public ImageService(OpenAiImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public ImageResponse generateImage(String prompt,
                                       int n,
                                       int width,
                                       int height){
//        ImageResponse response = imageModel.call(
//                new ImagePrompt(prompt));

        ImageResponse response = imageModel.call(
                new ImagePrompt(prompt,
                        OpenAiImageOptions.builder()
                                .model("dall-e-2")
                                .N(n)
                                .height(height)
                                .width(width).build())
        );
        return response;
    }
}
