package com.atlassian.springbootreact.domain.repository;

import com.atlassian.springbootreact.domain.model.Dashboard;

import java.util.Optional;

public interface DashboardRepository {
    Dashboard create(Dashboard dashboard);
    Optional<Dashboard> retrieveDashboard(Long dashboardId);
}
