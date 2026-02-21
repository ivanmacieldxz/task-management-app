package org.konge.taskmanagementapp.api.dto.user;

public record AuthedUserResponseDTO(
    Long id,
    String username,
    String email,
    String authToken
) { }
