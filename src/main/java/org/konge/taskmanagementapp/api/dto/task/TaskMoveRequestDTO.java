package org.konge.taskmanagementapp.api.dto.task;

import jakarta.validation.constraints.NotNull;

public record TaskMoveRequestDTO(
        @NotNull(message = "Target list ID is required.")
        Long targetListId,

        Double prevPosition,
        Double nextPosition
) { }