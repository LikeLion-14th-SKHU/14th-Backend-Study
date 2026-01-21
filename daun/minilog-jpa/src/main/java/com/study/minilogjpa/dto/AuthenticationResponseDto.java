package com.study.minilogjpa.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class AuthenticationResponseDto {
    @NonNull
    private String jwt;
}
