package org.konge.taskmanagementapp.api.dto.task;

import org.konge.taskmanagementapp.api.model.task.TaskPriority;

import java.time.LocalDateTime;

public record TaskCreationRequestDTO(
    String title,
    String description,
    TaskPriority priority,
    LocalDateTime dueDate
) { }
