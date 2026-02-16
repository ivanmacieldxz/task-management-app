package org.konge.taskmanagementapp.api.dto.user;

public record UserResponseDTO(
    Long id,
    String username,
    String email
) { }
