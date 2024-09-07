package org.kt.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;
import org.kt.backend.service.DashboardService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Dashboard {

    private final DashboardService dashboardService;

    public Dashboard(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @CrossOrigin()
    @GetMapping("/today-detected-emails")
    @Operation(summary = "금일 이메일 탐지 정보", description = "금일 이메일 탐지 및 차단 정보를 가져옵니다.")
    public Map<String, Object> getTodayDetectedEmails() {
        return dashboardService.getTodayEmailStats();
    }
}
