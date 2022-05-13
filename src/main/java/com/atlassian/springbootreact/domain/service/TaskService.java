package com.atlassian.springbootreact.domain.service;

import com.atlassian.springbootreact.domain.enums.StatusEnum;
import com.atlassian.springbootreact.domain.exception.NotFoundException;
import com.atlassian.springbootreact.domain.filterandpagination.ElementPage;
import com.atlassian.springbootreact.domain.filterandpagination.TaskFilter;
import com.atlassian.springbootreact.domain.model.Dashboard;
import com.atlassian.springbootreact.domain.model.Task;
import com.atlassian.springbootreact.domain.repository.DashboardRepository;
import com.atlassian.springbootreact.domain.repository.PublisherRepository;
import com.atlassian.springbootreact.domain.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class TaskService {

    public static final String TASK_NOT_FOUND = "task not found";
    private TaskRepository taskRepository;

    private PublisherRepository publisherRepository;

    private DashboardRepository dashboardRepository;

    public TaskService(TaskRepository taskRepository, DashboardRepository dashboardRepository,
                       PublisherRepository publisherRepository) {
        this.taskRepository = taskRepository;
        this.dashboardRepository = dashboardRepository;
        this.publisherRepository = publisherRepository;
    }
    public Task retrieveTask(Long taskId) {
        Optional<Task> taskOptional = taskRepository.retrieveById(taskId);

        if(taskOptional.isEmpty()){
            throw new NotFoundException(TASK_NOT_FOUND);
        } else {
            return taskOptional.get();
        }
    }

    public void deleteTask(Long taskId) {
        Optional<Task> optionalTask = taskRepository.retrieveById(taskId);

        if(optionalTask.isPresent()) {
            taskRepository.delete(taskId);
        } else {
            throw new NotFoundException(TASK_NOT_FOUND);
        }
    }

    public ElementPage<Task> retrieveTasks(TaskFilter taskFilter) {
        return taskRepository.retrieveTasks(taskFilter);
    }

    public Task save(Task task) {
        Optional<Dashboard> dashboardOptional = dashboardRepository.retrieveDashboard(task.getDashboardId());
        if(dashboardOptional.isPresent()) {
            return taskRepository.save(task);
        } else {
            throw new NotFoundException("The dashboard must exist in database");
        }

    }

    public Task updateStatus(Long taskId, StatusEnum status) {
        Optional<Task> optionalTask = taskRepository.retrieveById(taskId);

        if(optionalTask.isPresent()) {
           Task task =  optionalTask.get();
           task.setStatus(status);
           if(StatusEnum.DONE.equals(status)) {
               task.setEndDate(LocalDateTime.now());
              publisherRepository.sendMessage("Status changed to DONE");
           }
           return taskRepository.save(task);
        } else {
            throw new NotFoundException(TASK_NOT_FOUND);
        }
    }
}
