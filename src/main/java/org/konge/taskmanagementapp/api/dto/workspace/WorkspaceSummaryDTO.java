package org.konge.taskmanagementapp.api.dto.workspace;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WorkspaceSummaryDTO(
        Long id,
        String name,
        String description,
        Long ownerId,
        LocalDateTime createdAt
) {}
