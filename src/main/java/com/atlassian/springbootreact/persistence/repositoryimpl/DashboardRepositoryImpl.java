package com.atlassian.springbootreact.persistence.repositoryimpl;

import com.atlassian.springbootreact.domain.model.Dashboard;
import com.atlassian.springbootreact.domain.repository.DashboardRepository;
import com.atlassian.springbootreact.persistence.entity.DashboardEntity;
import com.atlassian.springbootreact.persistence.jpa.JpaDashboardRepository;
import com.atlassian.springbootreact.persistence.mapper.DashboardMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DashboardRepositoryImpl implements DashboardRepository {

    private JpaDashboardRepository jpaDashboardRepository;
    private DashboardMapper dashboardMapper;

    public DashboardRepositoryImpl(JpaDashboardRepository jpaDashboardRepository, DashboardMapper dashboardMapper) {
        this.jpaDashboardRepository = jpaDashboardRepository;
        this.dashboardMapper = dashboardMapper;
    }

    @Override
    public Dashboard create(Dashboard dashboard) {
        return dashboardMapper.convertEntityToDomain(
                jpaDashboardRepository.save(dashboardMapper.convertDomainToEntity(dashboard))
        );
    }

    @Override
    public Optional<Dashboard> retrieveDashboard(Long dashboardId) {
        Optional<DashboardEntity> optionalDashboardEntity = jpaDashboardRepository.findById(dashboardId);

        if (optionalDashboardEntity.isPresent()) {
            return Optional.of(dashboardMapper.convertEntityToDomain(optionalDashboardEntity.get()));
        } else {
            return Optional.empty();
        }
    }
}
