package org.example.javangersspringrecap.service;

import org.example.javangersspringrecap.model.Todo;
import org.example.javangersspringrecap.model.TodoStatus;
import org.example.javangersspringrecap.model.dto.TodoDTO;
import org.example.javangersspringrecap.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    private final TodoRepository repo = mock(TodoRepository.class);
    private final IdService idService = mock(IdService.class);

    @Test
    void getAllTodos_shouldReturnEmptyList_whenCalledInitially() {
        TodoService todoService = new TodoService(repo, idService);
        assertEquals(List.of(), todoService.getAllTodos());
    }

    @Test
    void getAllTodos_shouldReturnListOfOne_WhenRepoHasOneEntry() {
        TodoService todoService = new TodoService(repo, idService);
        Todo expected = new Todo("1", "test", TodoStatus.OPEN);
        when(repo.findAll()).thenReturn(List.of(expected));
        assertEquals(List.of(expected), todoService.getAllTodos());
    }

    @Test
    void getTodoById_shouldThrowException_whenTodoDoesNotExist() {
        TodoService todoService = new TodoService(repo, idService);
        assertThrows(ResponseStatusException.class, () -> todoService.getTodoById("DieseIdExistiertNicht"));
    }

    @Test
    void getTodoById_shouldReturnTodo_whenCalledWithExistingId() {
        TodoService todoService = new TodoService(repo, idService);
        Todo expected = new Todo("1", "test", TodoStatus.OPEN);
        when(repo.findById("1")).thenReturn(Optional.of(expected));

        Todo actual = todoService.getTodoById("1");

        assertEquals(expected, actual);
    }

    @Test
    void createTodo_shouldReturnTodo_whenCalledWithTodoDTO() {
        TodoService todoService = new TodoService(repo, idService);
        when(idService.generateId()).thenReturn("5");
        Todo expected = new Todo("5", "test", TodoStatus.OPEN);

        Todo actual = todoService.createTodo(new TodoDTO("test", TodoStatus.OPEN));

        assertEquals(expected, actual);
        verify(repo).save(expected);
    }

    @Test
    void updateTodo_shouldReturnTodo_whenCalledWithTodoDTO() {
        TodoService todoService = new TodoService(repo, idService);
        Todo todo = new Todo("10", "test", TodoStatus.OPEN);
        when(repo.findById("1")).thenReturn(Optional.of(todo));

        Todo expected = new Todo("10", "test2", TodoStatus.IN_PROGRESS);
        Todo actual = todoService.updateTodo("1", new TodoDTO("test2", TodoStatus.IN_PROGRESS));

        assertEquals(expected, actual);
        verify(repo).save(expected);
    }

    @Test
    void deleteTodo_shouldDeleteTodo_whenCalledWithId() {
        TodoService todoService = new TodoService(repo, idService);
        todoService.deleteTodo("1");
        verify(repo).deleteById("1");
    }
}