package com.atlassian.springbootreact.domain.repository;

import com.atlassian.springbootreact.domain.filterandpagination.ElementPage;
import com.atlassian.springbootreact.domain.filterandpagination.TaskFilter;
import com.atlassian.springbootreact.domain.model.Task;

import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> retrieveById(Long taskId);
    void delete(Long taskId);
    ElementPage<Task> retrieveTasks(TaskFilter taskFilter);
}
