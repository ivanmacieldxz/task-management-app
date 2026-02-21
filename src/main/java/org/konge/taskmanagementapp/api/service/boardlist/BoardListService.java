package org.konge.taskmanagementapp.api.service.boardlist;

import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.exception.ResourceNotFoundException;
import org.konge.taskmanagementapp.api.model.boardlist.BoardList;
import org.konge.taskmanagementapp.api.model.workspace.Workspace;
import org.konge.taskmanagementapp.api.repository.boardlist.BoardListRepository;
import org.konge.taskmanagementapp.api.repository.workspace.WorkspaceRepository;
import org.konge.taskmanagementapp.api.service.common.PositioningService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardListService {

    private final BoardListRepository boardListRepository;
    private final WorkspaceRepository workspaceRepository;

    private final PositioningService<BoardList> positioningService;

    @Transactional
    public BoardList createList(Long workspaceId, String name) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to create list: list not found."));

        List<BoardList> workspaceLists = boardListRepository.findByWorkspaceIdOrderByPositionInWorkspaceAsc(workspaceId);

        BoardList newList = BoardList.builder()
                .name(name)
                .workspace(workspace)
                .build();

        positioningService.insertLast(workspaceLists, newList, boardListRepository::save);

        return boardListRepository.save(newList);
    }

    @Transactional
    public BoardList moveListBetween(Long listId, Double positionPrevList, Double positionNextList) {
        BoardList listToMove = boardListRepository.findById(listId)
             .orElseThrow(() -> new ResourceNotFoundException("Unable to move list: list not found."));

        Workspace workspace = listToMove.getWorkspace();

        if (boardListRepository.countByWorkspaceId(workspace.getId()) < 2)
            throw new RuntimeException("Not enough lists to perform move.");

        List<BoardList> lists = boardListRepository.findByWorkspaceIdOrderByPositionInWorkspaceAsc(workspace.getId());

        if (positionPrevList == null)
            positioningService.moveFirst(
                    listToMove,
                    lists,
                    boardListRepository::save
            );
        else if (positionNextList == null)
            positioningService.moveLast(
                    listToMove,
                    lists,
                    boardListRepository::save
            );
        else
            positioningService.moveBetween(
                    positionPrevList,
                    positionNextList,
                    listToMove
            );

        return boardListRepository.save(listToMove);
    }

    @Transactional
    public BoardList updateList(
            Long id,
            String name,
            String description
    ) {
        BoardList toUpdate = boardListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Updating list failed: list not found."));

        toUpdate.setName(name);
        toUpdate.setDescription(description);

        return boardListRepository.save(toUpdate);
    }

    @Transactional
    public List<BoardList> findAll(Long workspaceId) {
        return boardListRepository.findByWorkspaceIdOrderByPositionInWorkspaceAsc(workspaceId);
    }

    @Transactional
    public void removeList(Long boardListId) {
        BoardList boardList = boardListRepository.findById(boardListId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to remove list: list not found."));

        boardListRepository.delete(boardList);
    }

    @Transactional(readOnly = true)
    public BoardList getListDetails(Long listId) {
        return boardListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Unable to get list details: list not found."));
    }
}
