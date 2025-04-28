package org.example.javangersspringrecap.model;

import java.time.LocalDateTime;

public record ErrorMessage(String message, LocalDateTime timestamp) {
}
