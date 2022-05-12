package com.atlassian.springbootreact.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "dashboard")
@Data
@NoArgsConstructor
public class DashboardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    public DashboardEntity(Long id) {
        this.id = id;
    }
}
