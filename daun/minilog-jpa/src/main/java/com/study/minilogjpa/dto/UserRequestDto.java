package com.study.minilogjpa.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UserRequestDto {
    @NonNull
    private String username;

    @NonNull
    private String password;
}
