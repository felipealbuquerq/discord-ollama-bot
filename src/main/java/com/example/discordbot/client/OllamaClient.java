package com.example.discordbot.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;

@Component
public class OllamaClient {

    private final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private final RestTemplate restTemplate = new RestTemplate();

    public String askModel(String model, String question) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", model);
            requestBody.put("prompt", question);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(OLLAMA_URL, entity, String.class);

            JSONObject responseBody = new JSONObject(response.getBody());
            return responseBody.optString("response", "No response from Ollama.");
        } catch (Exception e) {
            return "Error communicating with Ollama: " + e.getMessage();
        }
    }
}
