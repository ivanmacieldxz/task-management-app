package org.konge.taskmanagementapp.api.controller.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.dto.task.ChecklistItemDTO;
import org.konge.taskmanagementapp.api.dto.task.TaskRequestDTO;
import org.konge.taskmanagementapp.api.dto.task.TaskMoveRequestDTO;
import org.konge.taskmanagementapp.api.dto.task.TaskResponseDTO;
import org.konge.taskmanagementapp.api.model.task.ChecklistItem;
import org.konge.taskmanagementapp.api.model.task.Task;
import org.konge.taskmanagementapp.api.service.common.MappingService;
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
    private final MappingService mappingService;

    @GetMapping("/lists/{listId}/tasks")
    public ResponseEntity<List<TaskResponseDTO>> getTasksFromList(@PathVariable Long listId) {
        List<TaskResponseDTO> tasks = taskService.findAllByList(listId)
                .stream()
                .map(mappingService::mapTaskToDTO)
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/lists/{listId}/tasks")
    public ResponseEntity<TaskResponseDTO> createTask(
            @PathVariable Long listId,
            @Valid @RequestBody TaskRequestDTO request
    ) {
        Task newTask = taskService.createTask(
                listId,
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate()
        );

        return new ResponseEntity<>(mappingService.mapTaskToDTO(newTask), HttpStatus.CREATED);
    }

    @PatchMapping("/tasks/{taskId}/move")
    public ResponseEntity<TaskResponseDTO> moveTask(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskMoveRequestDTO request
    ) {
        Task movedTask = taskService.moveTask(
                taskId,
                request.targetListId(),
                request.prevPosition(),
                request.nextPosition()
        );

        return ResponseEntity.ok(mappingService.mapTaskToDTO(movedTask));
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskRequestDTO request
    ) {
        Task updatedTask = taskService.updateTask(
                taskId,
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate()
        );

        return ResponseEntity.ok(mappingService.mapTaskToDTO(updatedTask));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponseDTO> deleteTask(
            @PathVariable Long taskId
    ) {
        Task deleted = taskService.getTask(taskId);

        taskService.deleteTask(taskId);

        return ResponseEntity.ok(mappingService.mapTaskToDTO(deleted));
    }

    @PostMapping("/tasks/{taskId}/checklist")
    public ResponseEntity<TaskResponseDTO> addCheckListItem(
            @PathVariable Long taskId,
            @Valid @RequestBody ChecklistItemDTO request
    ) {
        Task updatedTask = taskService.addChecklistItem(taskId, request.description());

        return ResponseEntity.ok(mappingService.mapTaskToDTO(updatedTask));
    }

    @PatchMapping("/tasks/{taskId}/checklist/{index}")
    public ResponseEntity<TaskResponseDTO> updateChecklistItem(
            @PathVariable Long taskId,
            @PathVariable int index,
            @Valid @RequestBody ChecklistItemDTO request
    ) {
        Task updatedTask = taskService.updateChecklistItem(
                taskId,
                index,
                request.description(),
                request.completed()
        );

        return ResponseEntity.ok(mappingService.mapTaskToDTO(updatedTask));
    }

    @DeleteMapping("/tasks/{taskId}/checklist/{index}")
    public ResponseEntity<TaskResponseDTO> deleteChecklistItem(
            @PathVariable Long taskId,
            @PathVariable int index
    ) {
        Task updatedTask = taskService.removeChecklistItem(taskId, index);

        return ResponseEntity.ok(mappingService.mapTaskToDTO(updatedTask));
    }

}