package com.example.jongbum.service;

import com.example.jongbum.model.Todo;
import com.example.jongbum.repository.TodoInMemoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoInMemoryRepository todoInMemoryRepository;

    @Autowired
    public TodoService(TodoInMemoryRepository todoInMemoryRepository) {
        this.todoInMemoryRepository = todoInMemoryRepository;
    }

    public List<Todo> findAll() {
        return todoInMemoryRepository.findAll();
    }

    public Todo findById(Long id) {
        return todoInMemoryRepository.findById(id);
    }

    public Todo save(Todo todo) {
        return todoInMemoryRepository.save(todo);
    }

    public Todo update(Long id, Todo todo) {
        todo.setId(id);
        return todoInMemoryRepository.save(todo);
    }

    public void delete(Long id) {
        todoInMemoryRepository.deleteById(id);
    }
}
