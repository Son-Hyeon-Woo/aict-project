package org.kt.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;

import org.kt.backend.service.DashboardService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin()
@RestController
@RequestMapping("/api")
public class Dashboard {

    private final DashboardService dashboardService;

    public Dashboard(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/today-detected-emails")
    @Operation(summary = "금일 이메일 탐지 정보", description = "금일 이메일 탐지 및 차단 정보를 가져옵니다.")
    public Map<String, Object> getTodayDetectedEmails() {
        return dashboardService.getTodayEmailStats();
    }

    @GetMapping("/hourly-detected-emails")
    @Operation(summary = "시간별 이메일 탐지 정보", description = "금일과 전일의 최근 9시간의 시간별 이메일 탐지 정보를 가져옵니다.")
    public Map<String, Object> getHourlyEmailStats() {
        return dashboardService.getHourlyEmailStats();
    }

    @GetMapping("/count-by-risk-detail")
    @Operation(summary = "금일 이메일 유형별 갯수", description = "금일 이메일 유형별 갯수를 가져옵니다.")
    public Map<String, Object> getEmailsCountByRiskDetail() {
        return dashboardService.getEmailsCountByRiskDetail();
    }

    @GetMapping("/last-10-blocked-emails")
    @Operation(summary = "최근 10개 차단된 이메일", description = "최근 10개 차단된 이메일을 가져옵니다.")
    public Map<String, Object> getTop10BlockedEmails() {
        return dashboardService.getTop10BlockedEmails();
    }

    @GetMapping("/count-by-risk-detail-last-7-days")
    @Operation(summary = "최근 7일간 이메일 탐지 건수", description = "최근 7일간 이메일 탐지건수를 가져옵니다.")
    public Map<String, Object> getEmailsCountByRiskDetailLast7Days() {
        return dashboardService.getEmailsCountByRiskDetailLast7Days();
    }

    @GetMapping("/recent-emails")
    @Operation(summary = "최근 이메일", description = "최근 이메일을 가져옵니다.")
    public Map<String, Object> getRecentEmailsWithoutDetectionResult() {
        return dashboardService.getRecentEmailsWithoutDetectionResult();
    }
}
