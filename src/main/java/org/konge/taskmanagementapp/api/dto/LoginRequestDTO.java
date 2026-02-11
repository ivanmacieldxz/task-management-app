package org.konge.taskmanagementapp.api.dto;

public record LoginRequestDTO(
        String identifier,
        String password
) { }
