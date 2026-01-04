package com.asdf.todo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.asdf.todo.dto.TodoRequestDto;
import com.asdf.todo.dto.TodoResponseDto;
import com.asdf.todo.entity.Todo;
import com.asdf.todo.service.TodoService;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class TodoControllerTests {

    private MockMvc mockMvc;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
    }

    @Test
    void testGetTodoById() throws Exception {
        TodoResponseDto todo = new TodoResponseDto(1L, "Test Todo", "Description", false);

        given(todoService.findById(1L)).willReturn(todo);

        mockMvc.perform(get("/api/todos/v2/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Todo"));
    }

    @Test
    void testGetAllTodos() throws Exception {
        given(todoService.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/todos/v2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        given(todoService.findAll())
                .willReturn(Collections.singletonList(
                        new TodoResponseDto(1L, "Test Todo", "Description", false)));

        mockMvc.perform(get("/api/todos/v2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Todo"));
    }

    @Test
    void testCreateTodo() throws Exception {
        TodoResponseDto todo = new TodoResponseDto(1L, "New Todo", "Description", false);

        given(todoService.save(any(TodoRequestDto.class))).willReturn(todo);

        mockMvc.perform(post("/api/todos/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"New Todo\", \"description\":" + " \"Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("New Todo"));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        TodoResponseDto existingTodo =
                new TodoResponseDto(1L, "Existing Todo", "Description", false);

        TodoResponseDto updatedTodo =
                new TodoResponseDto(1L, "Updated Todo", "Updated Description", true);

        given(todoService.findById(1L)).willReturn(existingTodo);
        given(todoService.update(anyLong(), any(TodoRequestDto.class)))
                .willReturn(updatedTodo);

        mockMvc.perform(
                        put("/api/todos/v2/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        "{\"title\": \"Updated Todo\", \"description\": \"Updated Description\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Todo"));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        TodoResponseDto todo =
                new TodoResponseDto(1L, "Test Todo", "Description", false);

        given(todoService.findById(1L)).willReturn(todo);

        mockMvc.perform(
                        delete("/api/todos/v2/1")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }
}
