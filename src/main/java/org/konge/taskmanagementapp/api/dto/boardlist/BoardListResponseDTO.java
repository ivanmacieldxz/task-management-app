package org.konge.taskmanagementapp.api.dto.boardlist;

import lombok.Builder;

@Builder
public record BoardListResponseDTO(
        Long id,
        String name,
        String description,
        Double positionInWorkspace,
        Long workspace
) { }
