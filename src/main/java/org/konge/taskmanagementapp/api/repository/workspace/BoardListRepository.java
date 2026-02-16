package org.konge.taskmanagementapp.api.repository.workspace;

import org.konge.taskmanagementapp.api.model.workspaces.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {

    List<BoardList> findByWorkspaceIdOrderByPositionInBoardAsc(Long workspaceId);

    long countByWorkspaceId(Long workspaceId);
}
