package com.example.aidemo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiApi.FunctionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class OpenAiRestController {
	
	private final OpenAiChatClient chatClient;
	private final OpenAiApi.FunctionTool rectangleAreaTool;
	
	@Autowired
    public OpenAiRestController(OpenAiChatClient chatClient, OpenAiApi.FunctionTool rectangleAreaTool) {
        this.chatClient = chatClient;
        this.rectangleAreaTool = rectangleAreaTool;
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
		Prompt prompt = new Prompt(message,
				OpenAiChatOptions.builder().withFunction("rectangeleAreaFunction").build());
		ChatResponse response = chatClient.call(prompt);
		return response.getResult();
	}
	
	@GetMapping("/ai/tool")
	public Generation toolCalling(@RequestParam(value = "message") String message) {
		new UserMessage(message);
		
		List<FunctionTool> toolsList = List.of(rectangleAreaTool);
		Prompt prompt = new Prompt(message, OpenAiChatOptions.builder()
				.withTools(toolsList)
				.build());
		
		ChatResponse response = chatClient.call(prompt);
		return response.getResult();
	}
	
}
