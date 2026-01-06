package com.example.jongbum.controller;

import com.example.jongbum.dto.TodoRequestDto;
import com.example.jongbum.dto.TodoResponseDto;
import com.example.jongbum.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
public class TodoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Test
    public void testGetTodoById() throws Exception {
        TodoResponseDto todo = new TodoResponseDto(1L, "Test Todo", "Description", false);
        given(todoService.findById(1L)).willReturn(todo);

        mockMvc.perform(get("/api/todos/v2/1").accept(MediaType.APPLICATION_JSON))
                // .andExpect().andExpect()  <-- [삭제됨] 빈 호출 제거
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Todo"));
    }

    @Test
    public void testGetAllTodos() throws Exception {
        // Case 1: 목록이 비었을 때
        given(todoService.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/todos/v2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Case 2: 목록이 있을 때
        given(todoService.findAll())
                .willReturn(
                        Collections.singletonList(
                                new TodoResponseDto(1L, "Test Todo", "Description", false)
                        )
                );

        mockMvc.perform(get("/api/todos/v2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Test Todo"));
    }

    @Test
    public void testCreateTodo() throws Exception {
        TodoResponseDto todo = new TodoResponseDto(1L, "New Todo", "Description", false);

        given(todoService.save(any(TodoRequestDto.class))).willReturn(todo);

        mockMvc.perform(post("/api/todos/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Todo\", \"description\": \"Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("New Todo"));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        // [수정됨] 변수 선언 추가
        TodoResponseDto existingTodo = new TodoResponseDto(1L, "Original Todo", "Original Desc", false);
        TodoResponseDto updateTodo = new TodoResponseDto(1L, "Updated Todo", "Updated Description", true);

        given(todoService.findById(1L)).willReturn(existingTodo);
        given(todoService.update(anyLong(), any(TodoRequestDto.class))).willReturn(updateTodo);

        // [참고] URL에 ID가 포함되어야 하는지 확인 필요 (여기선 /1 추가함)
        mockMvc.perform(put("/api/todos/v2/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Todo\", \"description\": \"Updated Description\"}"))
                .andExpect(status().isOk()) // 보통 수정은 200 OK (Created는 201)
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Updated Todo"));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        TodoResponseDto todo = new TodoResponseDto(1L, "New Todo", "Description", false);

        given(todoService.findById(1L)).willReturn(todo);

        mockMvc.perform(delete("/api/todos/v2/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}