package com.example.discordbot.client;

import org.springframework.stereotype.Component;

@Component
public class OllamaClient {
    public String askModel(String model, String question) {
        // TODO: Implement Ollama API integration here
        return "Ollama response for model " + model + ": " + question;
    }
}
