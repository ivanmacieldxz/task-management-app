package org.konge.taskmanagementapp.api.repository.task;

import org.konge.taskmanagementapp.api.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByListIdOrderByPositionInListAsc(Long listId);

}
