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
            case "!bot:r1":
            case "!chat:r1":
            case "!ai:r1":
                return ollamaClient.askModel("deepseek-r1:7b", question);
            case "!ia:coder":
            case "!bot:coder": 
            case "!chat:coder": 
            case "!ai:coder":
                return ollamaClient.askModel("deepseek-coder:6.7b", question);
            case "!ia:amy-coder":
            case "!bot:amy-coder": 
            case "!chat:amy-coder": 
            case "!ai:amy-coder":
                return ollamaClient.askModel("amy-coder:latest", question);
            case "!ia:amy-thinkam": 
            case "!bot:amy-thinkam": 
            case "!chat:amy-thinkam": 
            case "!ai:amy-thinkam":
                return ollamaClient.askModel("amy-thinkam:latest", question);
            default:
                return "Unknown command. Please use !ai:r1, !ai:amy-coder, !ai:amy-thinkam or !ai:coder.";
        }
    }
}
