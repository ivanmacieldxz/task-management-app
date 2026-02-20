package org.konge.taskmanagementapp.api.dto.workspace;

import lombok.Builder;
import org.konge.taskmanagementapp.api.dto.boardlist.BoardListDetailDTO;
import org.konge.taskmanagementapp.api.dto.boardlist.BoardListSummaryDTO;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record WorkspaceDetailDTO(
        Long id,
        String name,
        String description,
        Long ownerId,
        List<BoardListSummaryDTO> lists,
        LocalDateTime createdAt,
        LocalDateTime lastModified
) {}
