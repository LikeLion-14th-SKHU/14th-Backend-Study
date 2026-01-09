package com.asdf.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


public record TodoResponseDto (

  @NotNull
  Long id,

  @NonNull
  String title,

  String description,
  boolean completed
){}
