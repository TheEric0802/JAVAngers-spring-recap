package org.example.javangersspringrecap.service;

import org.example.javangersspringrecap.model.openai.Message;
import org.example.javangersspringrecap.model.openai.Request;
import org.example.javangersspringrecap.model.openai.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class ChatGPTService {

    private final RestClient restClient;
    private final String aiModel;

    public ChatGPTService(RestClient.Builder restClientBuilder, @Value("${OPEN_AI_BASE_URL}") String baseUrl, @Value("${OPEN_AI_API_KEY}") String apiKey, @Value("${AI_MODEL}") String aiModel) {
        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
        this.aiModel = aiModel;
    }

    public String checkSpelling(String text) {
        Message devMessage = new Message("developer", "Prüfe den Text auf Rechtschreibfehler und gebe nur den korrigierten Text zurück.");
        Message userMessage = new Message("user", text);
        List<Message> messages = List.of(devMessage, userMessage);
        return restClient.post().contentType(MediaType.APPLICATION_JSON).body(new Request(aiModel, messages)).retrieve().body(Response.class).choices().get(0).message().content();
    }
}
