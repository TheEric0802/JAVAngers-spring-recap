package org.example.javangersspringrecap.controller;

import lombok.RequiredArgsConstructor;
import org.example.javangersspringrecap.model.Todo;
import org.example.javangersspringrecap.model.TodoStatus;
import org.example.javangersspringrecap.repository.TodoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController @RequestMapping("/api/todo") @RequiredArgsConstructor
public class TodoController {

    private final TodoRepository repo;

    @GetMapping
    public List<Todo> getAllTodos() {
        return repo.findAll();
    }
}
