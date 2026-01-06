package com.example.jongbum.util;

import com.example.jongbum.dto.TodoRequestDto;
import com.example.jongbum.dto.TodoResponseDto;
import com.example.jongbum.entity.Todo;

public class EntityDtoMapper {
    public static Todo toEntity(TodoRequestDto dto) {
        return new Todo(
                null,
                dto.getTitle(),
                dto.getDescription(),
                dto.isCompleted(),
                null
        );
    }

    public static TodoResponseDto toDto(Todo entity) {
        return new TodoResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.isCompleted()
        );
    }
}
