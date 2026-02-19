package org.konge.taskmanagementapp.api.controller.task;

import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.dto.task.TaskCreationRequestDTO;
import org.konge.taskmanagementapp.api.dto.task.TaskMoveRequestDTO;
import org.konge.taskmanagementapp.api.dto.task.TaskResponseDTO;
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
            @RequestBody TaskCreationRequestDTO request
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

    @PatchMapping("/tasks/{taskId}")
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

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long taskId
    ) {
        taskService.deleteTask(taskId);

        return ResponseEntity.ok("Task deleted successfully");
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
                .listId(task.getList().getId())
                .build();
    }
}