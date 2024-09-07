package org.kt.backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.kt.backend.repository.EmailRepository;
import org.springframework.stereotype.Service;

// NOTE - 이메일 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
@Service
public class DashboardService {

  private final EmailRepository emailRepository;

  public DashboardService(EmailRepository emailRepository) {
    this.emailRepository = emailRepository;
  }

  public Map<String, Object> getTodayEmailStats() {
    LocalDate today = LocalDate.now();
    LocalDateTime startOfDay = today.atStartOfDay();
    LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

    long totalEmails = emailRepository.countTodayReceivedEmails(startOfDay, endOfDay);
    long highRiskEmails = emailRepository.countTodayHighRiskEmails(startOfDay, endOfDay);
    long blockedHighRiskEmails = emailRepository.countTodayBlockedHighRiskEmails(startOfDay,
        endOfDay);

    Map<String, Long> data = new HashMap<>();

    data.put("totalEmails", totalEmails);
    data.put("highRiskEmails", highRiskEmails);
    data.put("blockedHighRiskEmails", blockedHighRiskEmails);

    Map<String, Object> response = new HashMap<>();

    response.put("status", "OK");
    response.put("message", "금일 이메일 탐지 정보 조회 성공");
    response.put("data", data);

    return response;
  }
}
