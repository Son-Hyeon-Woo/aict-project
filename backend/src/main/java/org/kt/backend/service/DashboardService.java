package org.kt.backend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;

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

  public Map<String, Object> getHourlyEmailStats() {
    LocalDateTime now = LocalDateTime.now();

    LocalDateTime recentFullHour = now.withMinute(0).withSecond(0).withNano(0);
    List<String> categories = IntStream.rangeClosed(0, 9)
        .mapToObj(i -> recentFullHour.minusHours(9 - i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00")))
        .collect(Collectors.toList());

    List<Long> todayData = categories.stream()
        .map(category -> {
          LocalDateTime start = LocalDateTime.parse(category, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00"));
          LocalDateTime end = start.plusHours(1);
          return emailRepository.countEmailsBetween(start, end);
        })
        .collect(Collectors.toList());

    List<Long> yesterdayData = categories.stream()
        .map(category -> {
          LocalDateTime start = LocalDateTime.parse(category, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00"))
              .minusDays(1);
          LocalDateTime end = start.plusHours(1);
          return emailRepository.countEmailsBetween(start, end);
        })
        .collect(Collectors.toList());

    Map<String, Object> data = new HashMap<>();
    data.put("categories", categories);
    data.put("series", List.of(
        Map.of("name", "금일", "data", todayData),
        Map.of("name", "전일", "data", yesterdayData)));

    Map<String, Object> response = new HashMap<>();
    response.put("status", "OK");
    response.put("message", "최근 10시간 이메일 탐지 정보 조회 성공");
    response.put("data", data);

    return response;
  }
}
