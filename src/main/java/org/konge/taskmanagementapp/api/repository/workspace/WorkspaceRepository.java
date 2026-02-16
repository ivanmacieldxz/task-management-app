package org.konge.taskmanagementapp.api.repository.workspace;

import org.konge.taskmanagementapp.api.model.workspaces.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    List<Workspace> findWorkspaceByOwnerId(Long ownerId);
}
