package com.example.aidemo.controller;

import java.util.List;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAiEmbeddingController {

	private final EmbeddingClient embeddingClient;
	
	@Autowired
	public OpenAiEmbeddingController(EmbeddingClient embeddingClient) {
		this.embeddingClient = embeddingClient;
	}
	
	@GetMapping("/ai/embedding")
    public EmbeddingResponse embed(@RequestParam(value = "message") String message) {
        EmbeddingResponse embeddingResponse = this.embeddingClient.embedForResponse(List.of(message));
        return embeddingResponse;
    } 
	
}
