package org.konge.taskmanagementapp.api.service.task;

import lombok.RequiredArgsConstructor;
import org.konge.taskmanagementapp.api.model.boardlist.BoardList;
import org.konge.taskmanagementapp.api.model.task.ChecklistItem;
import org.konge.taskmanagementapp.api.model.task.Task;
import org.konge.taskmanagementapp.api.model.task.TaskPriority;
import org.konge.taskmanagementapp.api.repository.boardlist.BoardListRepository;
import org.konge.taskmanagementapp.api.repository.task.TaskRepository;
import org.konge.taskmanagementapp.api.service.common.PositioningService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final BoardListRepository boardListRepository;
    private final PositioningService<Task> positioningService;

    @Transactional
    public Task createTask(Long listId, String title, String description, TaskPriority priority, LocalDateTime localDateTime) {
        BoardList list = boardListRepository.findById(listId)
                .orElseThrow(() -> new RuntimeException("Unable to create task: list not found."));

        List<Task> parentListTasks = taskRepository.findByListIdOrderByPositionInListAsc(list.getId());

        Task newTask = Task.builder()
                .title(title)
                .description(description)
                .priority(priority)
                .list(list)
                .build();

        positioningService.insertLast(parentListTasks, newTask, taskRepository::save);

        return taskRepository.save(newTask);
    }

    @Transactional
    public Task moveTask(Long taskId, Long targetListId, Double prevPos, Double nextPos) {
        Task taskToMove = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Unable to move task: task not found."));

        if (!taskToMove.getList().getId().equals(targetListId)) {
            BoardList targetList = boardListRepository.findById(targetListId)
                    .orElseThrow(() -> new RuntimeException("Unable to move task: target list not found."));

            taskToMove.setList(targetList);
        }

        List<Task> parentListTasks = taskRepository.findByListIdOrderByPositionInListAsc(targetListId);

        if (parentListTasks.size() < 2) {
            positioningService.insertLast(parentListTasks, taskToMove, taskRepository::save);
        }

        if (prevPos == null) {
            positioningService.moveFirst(taskToMove, parentListTasks, taskRepository::save);
        } else if (nextPos == null) {
            positioningService.moveLast(taskToMove, parentListTasks, taskRepository::save);
        } else {
            positioningService.moveBetween(prevPos, nextPos, taskToMove);
        }

        return taskRepository.save(taskToMove);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Unable to delete: task doesn't exist"));

        taskRepository.delete(task);
    }

    @Transactional
    public Task updateTask(
            Long taskId,
            String title,
            String description,
            TaskPriority priority,
            LocalDateTime dueDate
    ) {
        Task taskToUpdate = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Unable to update: task not found."));

        taskToUpdate.setTitle(title);
        taskToUpdate.setDescription(description);
        taskToUpdate.setPriority(priority);
        taskToUpdate.setDueDate(dueDate);

        return taskRepository.save(taskToUpdate);
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByList(Long listId) {
        return taskRepository.findByListIdOrderByPositionInListAsc(listId);
    }

    @Transactional
    public Task addChecklistItem(Long taskId, String description) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.getChecklist().add(
                ChecklistItem.builder()
                        .description(description)
                        .completed(false)
                        .build()
        );

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateChecklistItem(Long taskId, int itemIndex, String description, boolean completed) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (itemIndex < 0 || itemIndex >= task.getChecklist().size()) {
            throw new RuntimeException("Checklist item index out of bounds");
        }

        ChecklistItem item = task.getChecklist().get(itemIndex);

        item.setDescription(description);
        item.setCompleted(completed);

        return taskRepository.save(task);
    }

    @Transactional
    public Task removeChecklistItem(Long taskId, int itemIndex) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (itemIndex >= 0 && itemIndex < task.getChecklist().size()) {
            task.getChecklist().remove(itemIndex);
        }

        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }
}
