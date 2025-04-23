package org.example.javangersspringrecap.service;

import lombok.RequiredArgsConstructor;
import org.example.javangersspringrecap.model.Todo;
import org.example.javangersspringrecap.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repo;

    public List<Todo> getAllTodos() {
        return repo.findAll();
    }
}
