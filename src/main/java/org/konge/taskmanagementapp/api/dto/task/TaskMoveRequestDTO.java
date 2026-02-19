package org.konge.taskmanagementapp.api.dto.task;

public record TaskMoveRequestDTO(
        Long targetListId,
        Double prevPosition,
        Double nextPosition
) { }
