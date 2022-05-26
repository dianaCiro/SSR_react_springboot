package com.atlassian.springbootreact.testdatabuilder;

import com.atlassian.springbootreact.domain.enums.StatusEnum;
import com.atlassian.springbootreact.domain.model.Task;

import java.time.LocalDateTime;

public class TaskTestDataBuilder {

    private Long dashboardId;
    private StatusEnum status;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public TaskTestDataBuilder(){
        this.dashboardId = 1l;
        this.status = StatusEnum.TODO;
        this.description = "test";
        this.startDate = LocalDateTime.now();
    }

    public TaskTestDataBuilder setDashboardId(long dashboardId) {
        this.dashboardId = dashboardId;
        return this;
    }
    public Task build(){
       return Task.builder()
                .dashboardId(dashboardId)
                .status(status)
                .startDate(startDate)
                .description(description)
                .build();
    }


}

