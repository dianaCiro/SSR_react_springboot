package com.atlassian.springbootreact.domain.model;

import com.atlassian.springbootreact.application.config.JacksonConfiguration;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class Task {
    private Long id;

    @NotNull(message = "dashboardId cannot be null")
    private Long dashboardId;

    @NotBlank(message = "status is missing or invalid")
    private String status;

    @NotBlank(message = "description is missing or invalid")
    private String description;

    @NotNull(message = "startDate cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm'Z'")
    private LocalDateTime startDate;

    @NotNull(message = "endDate cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm'Z'")
    private LocalDateTime endDate;
}
