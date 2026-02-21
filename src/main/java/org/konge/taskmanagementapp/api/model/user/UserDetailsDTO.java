package org.konge.taskmanagementapp.api.model.user;

public record UserDetailsDTO(
        Long id,
        String username,
        String email
) { }
