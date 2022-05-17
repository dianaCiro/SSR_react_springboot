package com.atlassian.springbootreact.application.restcontroller;

import com.atlassian.springbootreact.domain.model.Dashboard;
import com.atlassian.springbootreact.domain.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/dashboard")
@Validated
@CrossOrigin
public class DashboardController {
    private DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dashboard> retrieveDashboardInformation(@PathVariable("id") Long dashboardId) {
        return ResponseEntity.ok(dashboardService.retrieveDashboard(dashboardId));
    }

    @PostMapping
    public ResponseEntity<Dashboard> createDashboard(@Valid @RequestBody Dashboard dashboard){
        return ResponseEntity.ok(dashboardService.createDashboard(dashboard));
    }
}
