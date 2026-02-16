package org.konge.taskmanagementapp.api.dto.workspace;

import java.time.LocalDateTime;

public record WorkspaceResponseDTO(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt
) {}
