package org.konge.taskmanagementapp.api.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChecklistItemDTO(
        @NotBlank
        @Size(max = 2000, message = "Description is too long.")
        String description,
        Boolean completed
) { }
