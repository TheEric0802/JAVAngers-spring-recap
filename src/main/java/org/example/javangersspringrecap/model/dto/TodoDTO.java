package org.example.javangersspringrecap.model.dto;

import lombok.Data;
import org.example.javangersspringrecap.model.TodoStatus;

@Data
public class TodoDTO {
    private final String description;
    private final TodoStatus status;
}
