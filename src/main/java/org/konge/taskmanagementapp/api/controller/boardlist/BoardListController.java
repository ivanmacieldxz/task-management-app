package org.konge.taskmanagementapp.api.controller.boardlist;

import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.dto.boardlist.BoardListRequestDTO;
import org.konge.taskmanagementapp.api.dto.boardlist.BoardListMoveRequestDTO;
import org.konge.taskmanagementapp.api.dto.boardlist.BoardListResponseDTO;
import org.konge.taskmanagementapp.api.model.boardlist.BoardList;
import org.konge.taskmanagementapp.api.service.boardlist.BoardListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces/{workspaceId}/lists")
@RequiredArgsConstructor
public class BoardListController {

    private final BoardListService boardListService;

    @GetMapping
    public ResponseEntity<List<BoardListResponseDTO>> getLists(
            @PathVariable Long workspaceId
    ) {
        List<BoardList> lists = boardListService.findAll(workspaceId);

        List<BoardListResponseDTO> responseDTOList = lists
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(responseDTOList);
    }

    @PostMapping
    public ResponseEntity<BoardListResponseDTO> createList(
            @PathVariable Long workspaceId,
            @RequestBody BoardListRequestDTO request
    ) {
        BoardList boardList = boardListService.createList(workspaceId, request.name());

        return new ResponseEntity<>(
                mapToResponse(boardList),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{listId}/move")
    public ResponseEntity<BoardListResponseDTO> moveList(
            @PathVariable Long listId,
            @RequestBody BoardListMoveRequestDTO boardListMoveRequestDTO
    ) {
        BoardList movedList = boardListService.moveListBetween(
                listId,
                boardListMoveRequestDTO.prevListPosition(),
                boardListMoveRequestDTO.nextListPosition()
        );

        return ResponseEntity.ok(mapToResponse(movedList));
    }

    @PatchMapping("/{listId}")
    public ResponseEntity<BoardListResponseDTO> updateList(
            @PathVariable Long listId,
            @RequestBody BoardListRequestDTO request
    ) {
        BoardList updated = boardListService.updateList(listId, request.name(), request.description());

        return ResponseEntity.ok(mapToResponse(updated));
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<String> deleteList(
            @PathVariable Long listId
    ) {
        boardListService.removeList(listId);

        return ResponseEntity.ok("List deleted successfully");
    }

    private BoardListResponseDTO mapToResponse(BoardList boardList) {
        return BoardListResponseDTO.builder()
                .id(boardList.getId())
                .name(boardList.getName())
                .description(boardList.getDescription())
                .positionInWorkspace(boardList.getPositionInWorkspace())
                .workspace(boardList.getWorkspace().getId())
                .build();
    }
}
