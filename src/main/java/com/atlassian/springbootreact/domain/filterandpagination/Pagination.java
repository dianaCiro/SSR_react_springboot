package com.atlassian.springbootreact.domain.filterandpagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {

    private String page;
    private String limit;
    private String sortColumn;
    private String sortDirection;

}