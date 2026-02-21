package org.konge.taskmanagementapp.api.service.common;

import org.konge.taskmanagementapp.api.dto.boardlist.BoardListDetailDTO;
import org.konge.taskmanagementapp.api.dto.boardlist.BoardListSummaryDTO;
import org.konge.taskmanagementapp.api.dto.task.ChecklistItemDTO;
import org.konge.taskmanagementapp.api.dto.task.TaskResponseDTO;
import org.konge.taskmanagementapp.api.dto.workspace.WorkspaceDetailDTO;
import org.konge.taskmanagementapp.api.dto.workspace.WorkspaceSummaryDTO;
import org.konge.taskmanagementapp.api.model.boardlist.BoardList;
import org.konge.taskmanagementapp.api.model.task.ChecklistItem;
import org.konge.taskmanagementapp.api.model.task.Task;
import org.konge.taskmanagementapp.api.model.workspace.Workspace;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    public WorkspaceSummaryDTO mapWorkspaceToSummaryDTO(Workspace workspace) {
        return WorkspaceSummaryDTO.builder()
                .id(workspace.getId())
                .name(workspace.getName())
                .description(workspace.getDescription())
                .ownerId(workspace.getOwner().getId())
                .createdAt(workspace.getCreatedAt())
                .build();
    }

    public WorkspaceDetailDTO mapWorkspaceToDetailDTO(Workspace workspace) {
        return WorkspaceDetailDTO.builder()
                .id(workspace.getId())
                .name(workspace.getName())
                .description(workspace.getDescription())
                .ownerId(workspace.getOwner().getId())
                .lists(
                        workspace.getLists()
                                .stream()
                                .map(this::mapBoardListToSummaryDTO)
                                .toList()
                )
                .createdAt(workspace.getCreatedAt())
                .lastModified(workspace.getLastModified())
                .build();
    }

    public BoardListSummaryDTO mapBoardListToSummaryDTO(BoardList boardList) {
        return new BoardListSummaryDTO(
                boardList.getId(),
                boardList.getName(),
                boardList.getPositionInWorkspace()
        );
    }

    public BoardListDetailDTO mapBoardListToDetailDTO(BoardList boardList) {
        return BoardListDetailDTO.builder()
                .id(boardList.getId())
                .name(boardList.getName())
                .description(boardList.getDescription())
                .positionInWorkspace(boardList.getPositionInWorkspace())
                .createdAt(boardList.getCreatedAt())
                .lastModified(boardList.getLastModified())
                .build();
    }

    public TaskResponseDTO mapTaskToDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .position(task.getPositionInList())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .lastModified(task.getLastModified())
                .checkList(
                        task.getChecklist().stream()
                                .map(this::mapChecklistItemToDTO)
                                .toList()
                )
                .listId(task.getList().getId())
                .build();
    }

    public ChecklistItemDTO mapChecklistItemToDTO(ChecklistItem checklistItem) {
        return new ChecklistItemDTO(checklistItem.getDescription(), checklistItem.isCompleted());
    }
}
