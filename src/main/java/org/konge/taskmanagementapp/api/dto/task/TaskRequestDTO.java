package org.konge.taskmanagementapp.api.dto.task;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.konge.taskmanagementapp.api.model.task.TaskPriority;

import java.time.LocalDateTime;

public record TaskRequestDTO(
        @NotBlank(message = "Task title is required.")
        @Size(max = 255, message = "Title must not exceed 255 characters.")
        String title,

        @Size(max = 2000, message = "Description is too long.")
        String description,

        @NotNull(message = "Priority is required.")
        TaskPriority priority,

        @FutureOrPresent(message = "Due date cannot be in the past.")
        LocalDateTime dueDate
) { }