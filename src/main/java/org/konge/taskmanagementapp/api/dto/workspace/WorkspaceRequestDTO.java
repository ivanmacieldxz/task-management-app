package org.konge.taskmanagementapp.api.dto.workspace;

public record WorkspaceRequestDTO(
        String name,
        String description,
        Long ownerId
) { }

