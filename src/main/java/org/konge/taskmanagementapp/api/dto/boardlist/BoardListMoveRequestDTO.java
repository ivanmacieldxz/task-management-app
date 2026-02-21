package org.konge.taskmanagementapp.api.dto.boardlist;

public record BoardListMoveRequestDTO(
        Double prevListPosition,
        Double nextListPosition
) { }
