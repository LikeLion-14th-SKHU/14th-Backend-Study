package com.asdf.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDto {

  @NonNull private Long id; // 할 일의 ID
  @NonNull private String title; // 할 일의 제목
  private String description; // 할 일에 대한 설명
  private boolean completed; // 할 일의 완료 여부
}
