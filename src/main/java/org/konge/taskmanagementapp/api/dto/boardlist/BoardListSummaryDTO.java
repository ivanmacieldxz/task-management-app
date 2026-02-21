package org.konge.taskmanagementapp.api.dto.boardlist;

public record BoardListSummaryDTO(
        Long id,
        String name,
        Double positionInWorkspace
) { }
