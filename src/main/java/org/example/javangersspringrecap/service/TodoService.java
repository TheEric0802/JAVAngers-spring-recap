package org.example.javangersspringrecap.service;

import lombok.RequiredArgsConstructor;
import org.example.javangersspringrecap.model.Todo;
import org.example.javangersspringrecap.model.dto.TodoDTO;
import org.example.javangersspringrecap.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service @RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repo;
    private final IdService idService;

    public List<Todo> getAllTodos() {
        return repo.findAll();
    }

    public Todo getTodoById(String id) {
        return repo.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
    }

    public Todo createTodo(TodoDTO todoDTO) {
        Todo todo = new Todo(idService.generateId(), todoDTO.getDescription(), todoDTO.getStatus());
        repo.save(todo);
        return todo;
    }
}
