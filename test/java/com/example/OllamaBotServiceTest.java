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
                .thenReturn("The capital of France is Paris.");

        String response = ollamaBotService.getModelResponse("!ia:r1", userQuestion);
        assertEquals("The capital of France is Paris.", response);
    }

    @Test
    void testGetResponseFromModel_coder() {
        String userQuestion = "How do I write a for loop in Python?";
        when(ollamaClient.askModel("deepseek-coder:6.7b", userQuestion))
                .thenReturn("You can write a for loop in Python like this: for i in range(10): print(i)");

        String response = ollamaBotService.getModelResponse("!ia:coder", userQuestion);
        assertTrue(response.contains("for i in range(10)"));
    }

    @Test
    void testUnknownCommand() {
        String userQuestion = "Tell me a joke!";
        String response = ollamaBotService.getModelResponse("!ia:unknown", userQuestion);
        assertEquals("Unknown command. Please use !ia:r1 or !ia:coder.", response);
    }
}