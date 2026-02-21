package org.konge.taskmanagementapp.api.dto.boardlist;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BoardListDetailDTO(
        Long id,
        String name,
        String description,
        Double positionInWorkspace,
        LocalDateTime createdAt,
        LocalDateTime lastModified
) {}
