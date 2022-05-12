package com.atlassian.springbootreact.domain.service;

import com.atlassian.springbootreact.domain.model.Dashboard;
import com.atlassian.springbootreact.domain.exception.NotFoundException;
import com.atlassian.springbootreact.domain.repository.DashboardRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class DashboardService {

    private DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public Dashboard retrieveDashboard(Long dashboardId){

        Optional<Dashboard> dashboardOptional = dashboardRepository.retrieveDashboard(dashboardId);

        if(dashboardOptional.isEmpty()){
            throw new NotFoundException("dashboard not found");
        } else {
            return dashboardOptional.get();
        }
    }

    public Dashboard createDashboard(Dashboard dashboard) {
        dashboard.setCreationDate(LocalDateTime.now());
        return dashboardRepository.create(dashboard);
    }
}
