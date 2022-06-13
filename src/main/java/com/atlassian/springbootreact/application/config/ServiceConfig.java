package com.atlassian.springbootreact.application.config;

import com.atlassian.springbootreact.domain.repository.DashboardRepository;
import com.atlassian.springbootreact.domain.repository.PublisherRepository;
import com.atlassian.springbootreact.domain.repository.TaskRepository;
import com.atlassian.springbootreact.domain.service.DashboardService;
import com.atlassian.springbootreact.domain.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public DashboardService createDashboardService(DashboardRepository dashboardRepository){
        return new DashboardService(dashboardRepository);
    }

    @Bean
    public TaskService createTaskService(TaskRepository taskRepository, DashboardRepository dashboardRepository,
                                         PublisherRepository publisherRepository, ObjectMapper objectMapper){
        return new TaskService(taskRepository, dashboardRepository, publisherRepository, objectMapper);
    }
}
