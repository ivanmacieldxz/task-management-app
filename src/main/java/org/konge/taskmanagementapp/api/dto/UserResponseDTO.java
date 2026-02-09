package org.konge.taskmanagementapp.api.dto;

public record UserResponseDTO(
    Long id,
    String username,
    String email
) { }
