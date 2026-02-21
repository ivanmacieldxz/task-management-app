package org.konge.taskmanagementapp.api.service.workspace;

import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.exception.ResourceNotFoundException;
import org.konge.taskmanagementapp.api.model.user.User;
import org.konge.taskmanagementapp.api.model.workspace.Workspace;
import org.konge.taskmanagementapp.api.repository.user.UserRepository;
import org.konge.taskmanagementapp.api.repository.workspace.WorkspaceRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Workspace> findWorkspacesForCurrentUser() {
        User user = getCurrentUser();
        return workspaceRepository.findWorkspaceByOwnerId(user.getId());
    }

    @Transactional
    public Workspace createWorkspace(Workspace workspace) {
        User user = getCurrentUser();

        workspace.setOwner(user);

        return workspaceRepository.save(workspace);
    }

    @Transactional(readOnly = true)
    public Workspace getWorkspaceDetails(Long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to get workspace details: workspace not found."));
    }

    @Transactional
    public Workspace updateWorkspace(Long workspaceId, String name, String description) {
        Workspace workspace = workspaceRepository.getReferenceById(workspaceId);

        workspace.setName(name);
        workspace.setDescription(description);

        return workspaceRepository.save(workspace);
    }

    @Transactional
    public Workspace deleteWorkspace(Long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to delete workspace: workspace not found."));

        workspaceRepository.delete(workspace);

        return workspace;
    }

    private User getCurrentUser() {
        String userEmail =  SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + userEmail + " found."));
    }
}
