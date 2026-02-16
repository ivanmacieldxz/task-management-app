package org.konge.taskmanagementapp.api.controller.workspace;

import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.dto.workspace.WorkspaceCreationRequestDTO;
import org.konge.taskmanagementapp.api.dto.workspace.WorkspaceResponseDTO;
import org.konge.taskmanagementapp.api.model.workspace.Workspace;
import org.konge.taskmanagementapp.api.service.workspace.WorkspaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<WorkspaceResponseDTO> createWorkspace(@RequestBody WorkspaceCreationRequestDTO workspaceCreationRequest) {
        Workspace workspace = Workspace.builder()
                .name(workspaceCreationRequest.name())
                .description(workspaceCreationRequest.description())
                .build();

        Workspace createdWorkspace = workspaceService.createWorkspace(workspace);

        return new ResponseEntity<>(
                mapToResponse(createdWorkspace),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceResponseDTO>> getAllWorkspaces() {
        List<WorkspaceResponseDTO> responses = workspaceService.findWorkspacesForCurrentUser()
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    private WorkspaceResponseDTO mapToResponse(Workspace workspace) {
        return new WorkspaceResponseDTO(
                workspace.getId(),
                workspace.getName(),
                workspace.getDescription(),
                workspace.getCreatedAt()
        );
    }
}
