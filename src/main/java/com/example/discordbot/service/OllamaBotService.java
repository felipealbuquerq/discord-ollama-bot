package com.example.discordbot.service;

import com.example.discordbot.client.OllamaClient;
import org.springframework.stereotype.Service;

@Service
public class OllamaBotService {

    private final OllamaClient ollamaClient;

    public OllamaBotService(OllamaClient ollamaClient) {
        this.ollamaClient = ollamaClient;
    }

    public String getModelResponse(String command, String question) {
        switch (command) {
            case "!ia:r1":
                return ollamaClient.askModel("deepseek-r1:7b", question);
            case "!ia:coder":
                return ollamaClient.askModel("deepseek-coder:6.7b", question);
            default:
                return "Unknown command. Please use !ia:r1 or !ia:coder.";
        }
    }
}