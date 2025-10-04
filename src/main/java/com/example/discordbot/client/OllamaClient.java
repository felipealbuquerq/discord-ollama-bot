package com.example.discordbot.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;
import java.nio.charset.Charset;
import org.springframework.http.converter.StringHttpMessageConverter;

@Component
public class OllamaClient {

    private final String OLLAMA_URL = "http://localhost:11434/api/generate";
    // Usa o RestTemplate configurado
    private final RestTemplate restTemplate = getRestTemplate();

    // Método privado para configurar o RestTemplate com Charset UTF-8
    private RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate();
        
        // 1. Remove conversores de String existentes para evitar conflitos de encoding
        template.getMessageConverters().removeIf(converter -> converter instanceof StringHttpMessageConverter);
        
        // 2. Adiciona um novo conversor de String forçando o Charset para UTF-8
        template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        
        return template;
    }

    public String askModel(String model, String question) {
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", model);
            requestBody.put("prompt", question);

            HttpHeaders headers = new HttpHeaders();
            // Boa prática: Garante que a requisição é enviada como UTF-8
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

            // O RestTemplate configurado irá decodificar a resposta como UTF-8
            ResponseEntity<String> response = restTemplate.postForEntity(OLLAMA_URL, entity, String.class);

            StringBuilder fullResponse = new StringBuilder();
            String responseBody = response.getBody();

            if (responseBody != null) {
                String[] lines = responseBody.split("\n");
                for (String line : lines) {
                    if (line.trim().isEmpty()) continue;
                    JSONObject jsonLine = new JSONObject(line);
                    
                    if (jsonLine.has("response")) {
                        fullResponse.append(jsonLine.getString("response"));
                    }
                    
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
