package com.atlassian.springbootreact.persistence.jpa;

import com.atlassian.springbootreact.persistence.entity.DashboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDashboardRepository extends JpaRepository<DashboardEntity, Long> {
}
