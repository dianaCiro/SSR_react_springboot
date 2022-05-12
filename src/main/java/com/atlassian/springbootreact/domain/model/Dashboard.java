package com.atlassian.springbootreact.domain.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class Dashboard {

    private Long id;

    @NotBlank(message = "Name is missing or invalid")
    private String name;

    private LocalDateTime creationDate;
}
