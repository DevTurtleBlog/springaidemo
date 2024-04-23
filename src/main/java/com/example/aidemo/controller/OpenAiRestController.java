package com.example.aidemo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class OpenAiRestController {
	
	private final OpenAiChatClient chatClient;
	
	@Autowired
    public OpenAiRestController(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
    } 
	
	@GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message") String message) {
        return Map.of("generation", chatClient.call(message));
    }
	
	@GetMapping("/ai/stream")
	public Flux<ChatResponse> stream(@RequestParam(value = "message") String message) {
		Prompt prompt = new Prompt(new UserMessage(message));
	    return chatClient.stream(prompt);
	}
	
	@GetMapping("/ai/function")
	public Generation functionCalling(@RequestParam(value = "message") String message) {
		UserMessage userMessage = new UserMessage(message);
		Prompt prompt = new Prompt(message,
				OpenAiChatOptions.builder().withFunction("rectangeleAreaFunction").build());
		ChatResponse response = chatClient.call(prompt);
		return response.getResult();
	}
	
}
