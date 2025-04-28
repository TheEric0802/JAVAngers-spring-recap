package org.example.javangersspringrecap.service;

import lombok.RequiredArgsConstructor;
import org.example.javangersspringrecap.exception.TodoNotFoundException;
import org.example.javangersspringrecap.model.Todo;
import org.example.javangersspringrecap.model.dto.TodoDTO;
import org.example.javangersspringrecap.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repo;
    private final IdService idService;
    private final ChatGPTService chatGPTService;

    public List<Todo> getAllTodos() {
        return repo.findAll();
    }

    public Todo getTodoById(String id) throws TodoNotFoundException {
        return repo.findById(id).orElseThrow( () -> new TodoNotFoundException("Todo with id " + id + " not found") );
    }

    public Todo createTodo(TodoDTO todoDTO) {
        String checkedText = chatGPTService.checkSpelling(todoDTO.getDescription());
        Todo todo = new Todo(idService.generateId(), checkedText, todoDTO.getStatus());
        repo.save(todo);
        return todo;
    }

    public Todo updateTodo(String id, TodoDTO todoDTO) throws TodoNotFoundException {
        Todo todo = getTodoById(id);
        todo.setDescription(todoDTO.getDescription());
        todo.setStatus(todoDTO.getStatus());
        repo.save(todo);
        return todo;
    }

    public void deleteTodo(String id) throws TodoNotFoundException {
        repo.deleteById(id);
    }
}
