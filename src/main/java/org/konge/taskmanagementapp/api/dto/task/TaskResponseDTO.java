package org.konge.taskmanagementapp.api.dto.task;

import lombok.Builder;
import org.konge.taskmanagementapp.api.model.task.TaskPriority;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record TaskResponseDTO(
        Long id,
        String title,
        String description,
        Double position,
        TaskPriority priority,
        LocalDateTime dueDate,
        Long listId,
        LocalDateTime createdAt,
        LocalDateTime lastModified,
        List<ChecklistItemDTO> checkList
) { }
