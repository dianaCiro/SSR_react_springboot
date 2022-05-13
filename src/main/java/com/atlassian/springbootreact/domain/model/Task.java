package com.atlassian.springbootreact.domain.model;

import com.atlassian.springbootreact.domain.enums.StatusEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class Task {
    private Long id;

    @NotNull(message = "dashboardId cannot be null")
    private Long dashboardId;

    @NotNull(message = "status is missing or invalid")
    private StatusEnum status;

    @NotBlank(message = "description is missing or invalid")
    private String description;

    @NotNull(message = "startDate cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm'Z'")
    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
