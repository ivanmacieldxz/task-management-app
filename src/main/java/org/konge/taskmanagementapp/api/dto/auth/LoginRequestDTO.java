package org.konge.taskmanagementapp.api.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "Identifier (email or username) is required.")
        String identifier,

        @NotBlank(message = "Password is required.")
        String password
) { }