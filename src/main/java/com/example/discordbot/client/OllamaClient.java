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

            StringBuilder fullResponse = new StringBuilder();
            String responseBody = response.getBody();

            if (responseBody != null) {
                // Processa cada linha do stream como um objeto JSON separado
                String[] lines = responseBody.split("\n");
                for (String line : lines) {
                    if (line.trim().isEmpty()) continue;
                    JSONObject jsonLine = new JSONObject(line);
                    
                    // Concatena a parte da resposta de cada linha
                    if (jsonLine.has("response")) {
                        fullResponse.append(jsonLine.getString("response"));
                    }
                    
                    // Verifica se o stream terminou
                    if (jsonLine.optBoolean("done", false)) {
                        break;
                    }
                }
            }

            return fullResponse.toString();

        } catch (Exception e) {
            return "Error communicating with Ollama: " + e.getMessage();
        }
    }
}
