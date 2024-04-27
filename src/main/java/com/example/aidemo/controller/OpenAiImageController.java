package com.example.aidemo.controller;

import java.util.Map;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageClient;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAiImageController {
	
	private final OpenAiImageClient imageClient;
	
	@Autowired
    public OpenAiImageController(OpenAiImageClient imageClient) {
        this.imageClient = imageClient;
    } 
	
	@GetMapping("/ai/image")
    public String generate(@RequestParam(value = "message") String message) {
        
		ImageResponse response = imageClient.call(
		        new ImagePrompt(message,
		        OpenAiImageOptions.builder()
		                .withQuality("hd")
		                .withN(1)
		                .withHeight(1024)
		                .withWidth(1024)
		                .build()));
		
		return response.getResult().getOutput().getB64Json();
    }

}
