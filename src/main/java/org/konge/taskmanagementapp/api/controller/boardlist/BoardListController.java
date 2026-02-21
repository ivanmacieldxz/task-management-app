package org.konge.taskmanagementapp.api.controller.boardlist;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.dto.boardlist.BoardListDetailDTO;
import org.konge.taskmanagementapp.api.dto.boardlist.BoardListRequestDTO;
import org.konge.taskmanagementapp.api.dto.boardlist.BoardListMoveRequestDTO;
import org.konge.taskmanagementapp.api.dto.boardlist.BoardListSummaryDTO;
import org.konge.taskmanagementapp.api.model.boardlist.BoardList;
import org.konge.taskmanagementapp.api.service.boardlist.BoardListService;
import org.konge.taskmanagementapp.api.service.common.MappingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces/{workspaceId}/lists")
@RequiredArgsConstructor
public class BoardListController {

    private final BoardListService boardListService;
    private final MappingService mappingService;

    @GetMapping
    public ResponseEntity<List<BoardListSummaryDTO>> getLists(
            @PathVariable Long workspaceId
    ) {
        List<BoardList> lists = boardListService.findAll(workspaceId);

        List<BoardListSummaryDTO> responseDTOList = lists
                .stream()
                .map(mappingService::mapBoardListToSummaryDTO)
                .toList();

        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("/{listId}")
    public ResponseEntity<BoardListDetailDTO> getListDetail(@PathVariable Long listId) {
        BoardList boardList = boardListService.getListDetails(listId);

        return ResponseEntity.ok(mappingService.mapBoardListToDetailDTO(boardList));
    }

    @PostMapping
    public ResponseEntity<BoardListSummaryDTO> createList(
            @PathVariable Long workspaceId,
            @Valid @RequestBody BoardListRequestDTO request
    ) {
        BoardList boardList = boardListService.createList(workspaceId, request.name());

        return new ResponseEntity<>(
                mappingService.mapBoardListToSummaryDTO(boardList),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{listId}/move")
    public ResponseEntity<BoardListSummaryDTO> moveList(
            @PathVariable Long listId,
            @RequestBody BoardListMoveRequestDTO boardListMoveRequestDTO
    ) {
        BoardList movedList = boardListService.moveListBetween(
                listId,
                boardListMoveRequestDTO.prevListPosition(),
                boardListMoveRequestDTO.nextListPosition()
        );

        return ResponseEntity.ok(mappingService.mapBoardListToSummaryDTO(movedList));
    }

    @PatchMapping("/{listId}")
    public ResponseEntity<BoardListSummaryDTO> updateList(
            @PathVariable Long listId,
            @Valid @RequestBody BoardListRequestDTO request
    ) {
        BoardList updated = boardListService.updateList(listId, request.name(), request.description());

        return ResponseEntity.ok(mappingService.mapBoardListToSummaryDTO(updated));
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<String> deleteList(
            @PathVariable Long listId
    ) {
        boardListService.removeList(listId);

        return ResponseEntity.ok("List deleted successfully");
    }
}
