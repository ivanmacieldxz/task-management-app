package org.konge.taskmanagementapp.api.dto.boardlist;

import lombok.Builder;

@Builder
public record BoardListResponseDTO(
        Long id,
        String name,
        Double positionInBoard,
        Long workspace
) { }
