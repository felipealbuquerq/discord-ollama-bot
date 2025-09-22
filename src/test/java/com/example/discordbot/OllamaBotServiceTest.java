package com.example.discordbot;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.discordbot.service.OllamaBotService;
import com.example.discordbot.client.OllamaClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class OllamaBotServiceTest {

    private OllamaClient ollamaClient;
    private OllamaBotService ollamaBotService;

    @BeforeEach
    void setUp() {
        ollamaClient = mock(OllamaClient.class);
        ollamaBotService = new OllamaBotService(ollamaClient);
    }

    @Test
    void testGetResponseFromModel_r1() {
        String userQuestion = "What is the capital of France?";
        when(ollamaClient.askModel("deepseek-r1:7b", userQuestion))
                .thenReturn("Ollama response for model deepseek-r1:7b: What is the capital of France?");

        String response = ollamaBotService.getModelResponse("!ia:r1", userQuestion);
        assertEquals("Ollama response for model deepseek-r1:7b: What is the capital of France?", response);
    }

    @Test
    void testGetResponseFromModel_coder() {
        String userQuestion = "How do I write a for loop in Python?";
        when(ollamaClient.askModel("deepseek-coder:6.7b", userQuestion))
                .thenReturn("Ollama response for model deepseek-coder:6.7b: How do I write a for loop in Python?");

        String response = ollamaBotService.getModelResponse("!ia:coder", userQuestion);
        assertEquals("Ollama response for model deepseek-coder:6.7b: How do I write a for loop in Python?", response);
    }

    @Test
    void testUnknownCommand() {
        String userQuestion = "Tell me a joke!";
        String response = ollamaBotService.getModelResponse("!ia:unknown", userQuestion);
        assertEquals("Unknown command. Please use !ia:r1 or !ia:coder.", response);
    }
}