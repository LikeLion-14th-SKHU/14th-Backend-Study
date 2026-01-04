package com.asdf.todo.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    private Long id;

    @NotNull
    private String title;

    private String description;

    private boolean completed;
}
