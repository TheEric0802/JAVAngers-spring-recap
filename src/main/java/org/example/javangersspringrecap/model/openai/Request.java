package org.example.javangersspringrecap.model.openai;

import java.util.List;

public record Request(String model, List<Message> messages) {}
