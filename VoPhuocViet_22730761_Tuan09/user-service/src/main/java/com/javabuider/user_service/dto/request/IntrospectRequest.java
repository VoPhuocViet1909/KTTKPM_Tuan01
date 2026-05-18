package com.javabuider.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record IntrospectRequest(
        @NotBlank(message = "Token is required")
        String token
) {
}