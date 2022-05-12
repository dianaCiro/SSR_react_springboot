package com.atlassian.springbootreact.persistence.mapper;

import com.atlassian.springbootreact.domain.filterandpagination.ElementPage;
import com.atlassian.springbootreact.domain.model.Task;
import com.atlassian.springbootreact.persistence.entity.DashboardEntity;
import com.atlassian.springbootreact.persistence.entity.TaskEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {
    @Qualifier("objectConverter")
    private ObjectMapper objectMapper;

    public TaskMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Task convertEntityToDomain(TaskEntity taskEntity) {
        return objectMapper.convertValue(taskEntity, Task.class);
    }

    public TaskEntity convertDomainToEntity(Task task) {
        TaskEntity taskEntity = objectMapper.convertValue(task, TaskEntity.class);
        taskEntity.setDashboardEntity(new DashboardEntity(task.getDashboardId()));
        return taskEntity;
    }

    public ElementPage<Task> convertPageToElementPage(Page<TaskEntity> taskEntities) {
        ElementPage<Task> elementPage = new ElementPage();
        List<Task> tasks = taskEntities.getContent().stream().map(taskEntity ->
                convertEntityToDomain(taskEntity)
        ).collect(Collectors.toList());
        elementPage.setElements(tasks);
        elementPage.setTotalElements(taskEntities.getTotalElements());
        elementPage.setTotalPages(taskEntities.getTotalPages());
        return elementPage;
    }
}
