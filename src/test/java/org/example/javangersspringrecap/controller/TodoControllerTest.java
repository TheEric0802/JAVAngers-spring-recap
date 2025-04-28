package org.example.javangersspringrecap.controller;

import org.example.javangersspringrecap.model.Todo;
import org.example.javangersspringrecap.model.TodoStatus;
import org.example.javangersspringrecap.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {"OPEN_AI_BASE_URL=https://example.com", "OPEN_AI_API_KEY=123", "AI_MODEL=model"}) @AutoConfigureMockMvc @AutoConfigureMockRestServiceServer
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TodoRepository repo;
    @Autowired
    private MockRestServiceServer mockServer;

    @Test
    void getAllTodos_shouldReturnEmptyList_whenCalledInitially() throws Exception {
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllTodos_shouldReturnListOfOne_whenCalled() throws Exception {
        repo.save(new Todo("1", "test", TodoStatus.OPEN));

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    [
                        {
                            "id": "1",
                            "description": "test",
                            "status": "OPEN"
                        }
                    ]
                """));
    }

    @Test
    void getTodoById_shouldReturnTodo_whenCalledWithExistingId() throws Exception {
        repo.save(new Todo("2", "test2", TodoStatus.OPEN));

        mockMvc.perform(get("/api/todo/2"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                        "id": "2",
                        "description": "test2",
                        "status": "OPEN"
                    }
                """));
    }

    @Test
    void getTodoById_shouldReturn404_whenCalledWithNonExistingId() throws Exception {
        mockMvc.perform(get("/api/todo/3000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTodo_shouldReturnTodo_WhenCalledWithTodoDTO() throws Exception {
        mockServer.expect(requestTo("https://example.com"))
                        .andExpect(method(HttpMethod.POST))
                        .andRespond(withSuccess("""
                                {
                                    "choices": [
                                        {
                                            "message": {
                                                "content": "test"
                                            }
                                        }
                                    ]
                                }
                            """, MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "description": "test",
                        "status": "OPEN"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                    {
                        "description": "test",
                        "status": "OPEN"
                    }
                """))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void updateTodo_shouldReturnTodo_whenCalledWithTodoDTO() throws Exception {
        repo.save(new Todo("3", "test3", TodoStatus.OPEN));

        mockMvc.perform(put("/api/todo/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "description": "test4",
                        "status": "DONE"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                        "id": "3",
                        "description": "test4",
                        "status": "DONE"
                    }
                """));
    }

    @Test
    void deleteTodo_shouldReturn204_whenCalledWithExistingId() throws Exception {
        repo.save(new Todo("4", "test4", TodoStatus.DONE));

        mockMvc.perform(delete("/api/todo/4"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTodo_shouldReturn404_whenCalledWithNonExistingId() throws Exception {
        mockMvc.perform(delete("/api/todo/3000"))
                .andExpect(status().isNotFound());
    }
}