package org.example.javangersspringrecap.controller;

import lombok.RequiredArgsConstructor;
import org.example.javangersspringrecap.model.Todo;
import org.example.javangersspringrecap.model.dto.TodoDTO;
import org.example.javangersspringrecap.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/todo") @RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable String id) {
        return todoService.getTodoById(id);
    }

    @PostMapping
    public Todo createTodo(@RequestBody TodoDTO todoDTO) {
        return todoService.createTodo(todoDTO);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody TodoDTO todoDTO) {
        return todoService.updateTodo(id, todoDTO);
    }
}
