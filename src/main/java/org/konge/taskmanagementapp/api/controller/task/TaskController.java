package org.konge.taskmanagementapp.api.controller.task;

import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.dto.task.ChecklistItemDTO;
import org.konge.taskmanagementapp.api.dto.task.TaskRequestDTO;
import org.konge.taskmanagementapp.api.dto.task.TaskMoveRequestDTO;
import org.konge.taskmanagementapp.api.dto.task.TaskResponseDTO;
import org.konge.taskmanagementapp.api.model.task.ChecklistItem;
import org.konge.taskmanagementapp.api.model.task.Task;
import org.konge.taskmanagementapp.api.service.task.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/lists/{listId}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getTasksFromList(@PathVariable Long listId) {
        List<TaskResponseDTO> tasks = taskService.findAllByList(listId)
                .stream()
                .map(this::mapToResponse)
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/lists/{listId}/tasks")
    public ResponseEntity<TaskResponseDTO> createTask(
            @PathVariable Long listId,
            @RequestBody TaskRequestDTO request
    ) {
        Task newTask = taskService.createTask(
                listId,
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate()
        );

        return new ResponseEntity<>(mapToResponse(newTask), HttpStatus.CREATED);
    }

    @PatchMapping("/tasks/{taskId}/move")
    public ResponseEntity<TaskResponseDTO> moveTask(
            @PathVariable Long taskId,
            @RequestBody TaskMoveRequestDTO request
    ) {
        Task movedTask = taskService.moveTask(
                taskId,
                request.targetListId(),
                request.prevPosition(),
                request.nextPosition()
        );

        return ResponseEntity.ok(mapToResponse(movedTask));
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequestDTO request
    ) {
        Task updatedTask = taskService.updateTask(
                taskId,
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate()
        );

        return ResponseEntity.ok(mapToResponse(updatedTask));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long taskId
    ) {
        taskService.deleteTask(taskId);

        return ResponseEntity.ok("Task deleted successfully");
    }

    @PostMapping("/tasks/{taskId}/checklist")
    public ResponseEntity<TaskResponseDTO> addCheckListItem(
            @PathVariable Long taskId,
            @RequestBody ChecklistItemDTO request
    ) {
        Task updatedTask = taskService.addChecklistItem(taskId, request.description());

        return ResponseEntity.ok(mapToResponse(updatedTask));
    }

    @PatchMapping("/tasks/{taskId}/checklist/{index}")
    public ResponseEntity<TaskResponseDTO> updateChecklistItem(
            @PathVariable Long taskId,
            @PathVariable int index,
            @RequestBody ChecklistItemDTO request
    ) {
        Task updatedTask = taskService.updateChecklistItem(
                taskId,
                index,
                request.description(),
                request.completed()
        );

        return ResponseEntity.ok(mapToResponse(updatedTask));
    }

    @DeleteMapping("/tasks/{taskId}/checklist/{index}")
    public ResponseEntity<TaskResponseDTO> deleteChecklistItem(
            @PathVariable Long taskId,
            @PathVariable int index) {

        Task updatedTask = taskService.removeChecklistItem(taskId, index);
        return ResponseEntity.ok(mapToResponse(updatedTask));
    }

    private TaskResponseDTO mapToResponse(Task task) {
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
                        mapChecklistToResponse(task.getChecklist())
                )
                .listId(task.getList().getId())
                .build();
    }

    private List<ChecklistItemDTO> mapChecklistToResponse(List<ChecklistItem> list) {
        return list.stream()
                .map(checklistItem ->
                        new ChecklistItemDTO(checklistItem.getDescription(), checklistItem.isCompleted())
                )
                .toList();
    }
}