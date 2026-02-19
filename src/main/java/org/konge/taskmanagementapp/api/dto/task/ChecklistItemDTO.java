package org.konge.taskmanagementapp.api.dto.task;

public record ChecklistItemDTO(
        String description,
        Boolean completed
) { }
