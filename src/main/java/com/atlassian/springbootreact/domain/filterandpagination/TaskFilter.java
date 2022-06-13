package com.atlassian.springbootreact.domain.filterandpagination;

import lombok.Data;

@Data
public class TaskFilter extends Pagination {
    private Long dashboardId;
}
