package org.konge.taskmanagementapp.api.dto.workspace;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WorkspaceSummaryDTO(
        @NotNull
        Long id,
        @NotBlank(message = "Workspace name is required.")
        @Size(max = 50, message = "Workspace name must not exceed 64 characters.")
        String name,
        @Size(max = 500, message = "Description must not exceed 500 characters.")
        String description,
        Long ownerId,
        LocalDateTime createdAt
) {}
