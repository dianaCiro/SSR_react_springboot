package com.atlassian.springbootreact.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {

    private Long id;

    @NotBlank(message = "Name is missing or invalid")
    private String name;

    private LocalDateTime creationDate;
}
