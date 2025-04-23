package org.example.javangersspringrecap.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Todo {
    private String id;
    private String description;
    private TodoStatus status;
}
