package org.konge.taskmanagementapp.api.controller.workspace;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.dto.workspace.WorkspaceDetailDTO;
import org.konge.taskmanagementapp.api.dto.workspace.WorkspaceRequestDTO;
import org.konge.taskmanagementapp.api.dto.workspace.WorkspaceSummaryDTO;
import org.konge.taskmanagementapp.api.model.workspace.Workspace;
import org.konge.taskmanagementapp.api.service.common.MappingService;
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
    private final MappingService mappingService;

    @PostMapping
    public ResponseEntity<WorkspaceSummaryDTO> createWorkspace(@Valid @RequestBody WorkspaceRequestDTO workspaceCreationRequest) {
        Workspace workspace = Workspace.builder()
                .name(workspaceCreationRequest.name())
                .description(workspaceCreationRequest.description())
                .build();

        Workspace createdWorkspace = workspaceService.createWorkspace(workspace);

        return new ResponseEntity<>(
                mappingService.mapWorkspaceToSummaryDTO(createdWorkspace),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceSummaryDTO>> getAllWorkspaces() {
        List<WorkspaceSummaryDTO> responses = workspaceService.findWorkspacesForCurrentUser()
                .stream()
                .map(mappingService::mapWorkspaceToSummaryDTO)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceDetailDTO> getWorkspace(@PathVariable Long workspaceId) {
        Workspace workspace = workspaceService.getWorkspaceDetails(workspaceId);

        return ResponseEntity.ok(mappingService.mapWorkspaceToDetailDTO(workspace));
    }

    @PatchMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceSummaryDTO> updateWorkspace(@PathVariable Long workspaceId, @RequestBody WorkspaceSummaryDTO request) {
        Workspace workspace = workspaceService.updateWorkspace(workspaceId, request.name(), request.description());

        return ResponseEntity.ok(mappingService.mapWorkspaceToSummaryDTO(workspace));
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceSummaryDTO> deleteWorkspace(@PathVariable Long workspaceId) {
        Workspace workspace = workspaceService.deleteWorkspace(workspaceId);

        return ResponseEntity.ok(mappingService.mapWorkspaceToSummaryDTO(workspace));
    }

}
