package com.atlassian.springbootreact.persistence.mapper;

import com.atlassian.springbootreact.domain.model.Dashboard;
import com.atlassian.springbootreact.persistence.entity.DashboardEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DashboardMapper {

    @Qualifier("objectConverter")
    private ObjectMapper objectMapper;

    public DashboardMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Dashboard convertEntityToDomain(DashboardEntity dashboardEntity) {
        return objectMapper.convertValue(dashboardEntity, Dashboard.class);
    }

    public DashboardEntity convertDomainToEntity(Dashboard dashboard) {
        return objectMapper.convertValue(dashboard, DashboardEntity.class);
    }
}
