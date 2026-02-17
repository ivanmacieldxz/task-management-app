package org.konge.taskmanagementapp.api.service.boardlist;

import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.model.boardlist.BoardList;
import org.konge.taskmanagementapp.api.model.workspace.Workspace;
import org.konge.taskmanagementapp.api.repository.boardlist.BoardListRepository;
import org.konge.taskmanagementapp.api.repository.workspace.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardListService {

    private static final double BOARDLIST_TABLE_MAX_POSITION = Double.MAX_VALUE;
    private static final double BOARDLIST_TABLE_MIN_POSITION = 0.0;

    private final BoardListRepository boardListRepository;
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public BoardList createList(Long workspaceId, String name) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));

        Double position = arrangeListsAndGetLastPosition(workspaceId);

        BoardList newList = BoardList.builder()
                .name(name)
                .workspace(workspace)
                .positionInBoard(position)
                .build();

        return boardListRepository.save(newList);
    }

    @Transactional
    public BoardList moveListBetween(Long listId, Double positionPrevList, Double positionNextList) {
        BoardList list = boardListRepository.findById(listId)
             .orElseThrow(() -> new RuntimeException("List not found"));

        Long workspaceId = list.getWorkspace().getId();

        if (boardListRepository.countByWorkspaceId(workspaceId) < 2)
            throw new RuntimeException("Not enough lists to perform move.");

        Double prevPosition = list.getPositionInBoard();

        Double newPosition = arrangeListsAndGetPositionBetween(workspaceId, prevPosition, positionPrevList, positionNextList);
        list.setPositionInBoard(newPosition);

        return boardListRepository.save(list);
    }

    @Transactional
    public List<BoardList> findAll(Long workspaceId) {
        return boardListRepository.findByWorkspaceIdOrderByPositionInBoardAsc(workspaceId);
    }

    @Transactional
    public void removeList(Long boardListId) {
        BoardList boardList = boardListRepository.findById(boardListId)
                .orElseThrow(() -> new RuntimeException("List not found"));

        boardListRepository.delete(boardList);
    }

    /**
     * Returns inserting position for new list and rearranges last list's position if applicable.
     * @param workspaceId list's workspace's id
     * @return new list's position.
     */
    private double arrangeListsAndGetLastPosition(Long workspaceId) {
        long currentCount = boardListRepository.countByWorkspaceId(workspaceId);

        if (currentCount == 0) {
            return BOARDLIST_TABLE_MIN_POSITION;
        }

        if (currentCount == 1) {
            return BOARDLIST_TABLE_MAX_POSITION;
        }

        rearrangeForLastInsert(workspaceId);

        return BOARDLIST_TABLE_MAX_POSITION;
    }

    /**
     * Returns new list's position when inserting between positionPrevList and positionNextList.<br>
     * When positionPrevList is null, rearranges first list's position.<br>
     * When positionNextList is null, rearranges last list's position.<br>
     * When no position is null, no arrange is needed.
     * @param workspaceId list's workspace's id
     * @param positionPrevList previous list's position. Should be null when inserting first.
     * @param positionNextList next list's position. Should be null when inserting last.
     * @return new list's position.
     */
    private double arrangeListsAndGetPositionBetween(Long workspaceId, Double prevPosition, Double positionPrevList, Double positionNextList) {
        double newPosition;

        if (positionPrevList == null) {
            newPosition = BOARDLIST_TABLE_MIN_POSITION;

            if (prevPosition != newPosition)
                rearrangeForFirstInsert(workspaceId);
        } else if (positionNextList == null) {
            newPosition = BOARDLIST_TABLE_MAX_POSITION;

            if (prevPosition != newPosition)
                rearrangeForFirstInsert(workspaceId);
        } else {
            newPosition = (positionPrevList + positionNextList) / 2;
        }

        return newPosition;
    }

    /**
     * Rearranges last list's position and saves it.
     * @param workspaceId list's workspace's id.
     */
    private void rearrangeForLastInsert(Long workspaceId) {
        if (boardListRepository.countByWorkspaceId(workspaceId) == 2) {
            swapFirstAndLastLists(workspaceId);

            return;
        }

        List<BoardList> boardLists = boardListRepository.findByWorkspaceIdOrderByPositionInBoardAsc(workspaceId);

        BoardList last = boardLists.getLast();

        BoardList secondToLast = boardLists.get(
                boardLists.size() - 2
        );

        last.setPositionInBoard(
                secondToLast.getPositionInBoard() / 2 + BOARDLIST_TABLE_MAX_POSITION / 2
        );

        boardListRepository.save(last);
    }

    /**
     * Rearranges first list's position and saves it.
     * @param workspaceId list's workspace's id.
     */
    private void rearrangeForFirstInsert(Long workspaceId) {
        if (boardListRepository.countByWorkspaceId(workspaceId) == 2) {
            swapFirstAndLastLists(workspaceId);

            return;
        }

        List<BoardList> boardLists = boardListRepository.findByWorkspaceIdOrderByPositionInBoardAsc(workspaceId);

        BoardList first = boardLists.getFirst();
        BoardList second = boardLists.get(1);

        first.setPositionInBoard(
                second.getPositionInBoard() / 2
        );

        boardListRepository.save(first);
    }

    /**
     * Swaps first's and last's positions.
     * @param workspaceId list's workspace's id.
     */
    private void swapFirstAndLastLists(Long workspaceId) {
        List<BoardList> boardLists = boardListRepository.findByWorkspaceIdOrderByPositionInBoardAsc(workspaceId);

        BoardList first = boardLists.getFirst();
        BoardList last = boardLists.getLast();

        Double firstPosition = first.getPositionInBoard();

        first.setPositionInBoard(last.getPositionInBoard());
        last.setPositionInBoard(firstPosition);

        boardListRepository.save(first);
        boardListRepository.save(last);
    }
}
