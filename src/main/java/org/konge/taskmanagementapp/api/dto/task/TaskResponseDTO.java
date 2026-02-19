package org.konge.taskmanagementapp.api.dto.task;

import lombok.Builder;
import org.konge.taskmanagementapp.api.model.task.TaskPriority;

import java.time.LocalDateTime;

@Builder
public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        TaskPriority priority,
        LocalDateTime dueDate,
        Double position,
        Long listId,
        LocalDateTime createdAt,
        LocalDateTime lastModified
) { }
