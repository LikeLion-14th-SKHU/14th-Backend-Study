package com.asdf.todo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;


public record TodoRequestDto (

  @NotNull
  String title,

  @NotNull
  String description,

  boolean completed
){


  public TodoRequestDto(String title, String description) {
    this(title, description, false);
  }
}
