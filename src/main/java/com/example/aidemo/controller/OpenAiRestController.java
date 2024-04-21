package com.example.aidemo.controller;

import java.util.Map;

import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAiRestController {
	
	private final OpenAiChatClient chatClient;
	
	@Autowired
    public OpenAiRestController(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
    }
	
	@GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatClient.call(message));
    }
	
}
