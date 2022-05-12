package com.atlassian.springbootreact.domain.filterandpagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElementPage<T> {

    private List<T> elements;
    private int totalPages;
    private long totalElements;

}
