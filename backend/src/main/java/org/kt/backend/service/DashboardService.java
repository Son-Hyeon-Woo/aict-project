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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.kt.backend.entity.Email;

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
    LocalDateTime lastUpdateTime = LocalDateTime.now();

    Map<String, Object> data = new HashMap<>();

    data.put("totalEmails", totalEmails);
    data.put("highRiskEmails", highRiskEmails);
    data.put("blockedHighRiskEmails", blockedHighRiskEmails);
    data.put("lastUpdateTime", lastUpdateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

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
    LocalDateTime lastUpdateTime = LocalDateTime.now();

    Map<String, Object> data = new HashMap<>();
    data.put("categories", categories);
    data.put("series", List.of(
        Map.of("name", "금일", "data", todayData),
        Map.of("name", "전일", "data", yesterdayData)));
    data.put("lastUpdateTime", lastUpdateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    Map<String, Object> response = new HashMap<>();
    response.put("status", "OK");
    response.put("message", "최근 10시간 이메일 탐지 정보 조회 성공");
    response.put("data", data);

    return response;
  }

  public Map<String, Object> getEmailsCountByRiskDetail() {
    LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
    LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
    List<Object[]> results = emailRepository.countEmailsByRiskDetail(startOfDay, endOfDay);
    System.out.println(results);
    Map<String, Long> countEmailsByRiskDetail = new HashMap<>();
    for (Object[] result : results) {
      countEmailsByRiskDetail.put((String) result[0], (Long) result[1]);
    }

    LocalDateTime lastUpdateTime = LocalDateTime.now();
    Map<String, Object> data = new HashMap<>();
    data.put("emailCountByRiskDetail", countEmailsByRiskDetail);
    data.put("lastUpdateTime", lastUpdateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    Map<String, Object> response = new HashMap<>();
    response.put("status", "OK");
    response.put("message", "금일 이메일 유형별 갯수 조회 성공");
    response.put("data", data);

    return response;
  }

  public Map<String, Object> getTop10BlockedEmails() {
    List<Email> emails = emailRepository.findTop10BlockedEmailsOrderByDetectionDate(PageRequest.of(0, 10));
    List<Map<String, Object>> emailData = emails.stream().map(email -> {
      Map<String, Object> emailInfo = new HashMap<>();
      emailInfo.put("title", email.getSubject());
      emailInfo.put("riskLevel", email.getRiskLevel().getRiskLevel());
      emailInfo.put("riskDetail", email.getRiskLevel().getRiskDetail());
      emailInfo.put("detectionDate",
          email.getRiskLevel().getDetectionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
      return emailInfo;
    }).collect(Collectors.toList());

    LocalDateTime lastUpdateTime = LocalDateTime.now();
    Map<String, Object> data = new HashMap<>();
    data.put("emails", emailData);
    data.put("lastUpdateTime", lastUpdateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    Map<String, Object> response = new HashMap<>();
    response.put("status", "OK");
    response.put("message", "차단된 이메일 목록 조회 성공");
    response.put("data", data);

    return response;
  }

  public Map<String, Object> getEmailsCountByRiskDetailLast7Days() {
    LocalDate currentDate = LocalDate.now();
    Map<String, Long> emailCountMap = new HashMap<>();

    for (int i = 0; i < 7; i++) {
      LocalDateTime startOfDay = currentDate.atStartOfDay();
      LocalDateTime endOfDay = currentDate.atTime(LocalTime.MAX);

      long totalEmails = emailRepository.countEmailsByRiskDetailNotNoneOnDate(startOfDay, endOfDay);
      emailCountMap.put(currentDate.toString(), totalEmails);

      currentDate = currentDate.minusDays(1);
    }

    LocalDateTime lastUpdateTime = LocalDateTime.now();
    Map<String, Object> data = new HashMap<>();
    data.put("emailCountByRiskDetail", emailCountMap);
    data.put("lastUpdateTime", lastUpdateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    Map<String, Object> response = new HashMap<>();
    response.put("status", "OK");
    response.put("message", "최근 7일간 이메일 유형별 갯수 조회 성공");
    response.put("data", data);

    return response;
  }

  public Map<String, Object> getRecentEmailsWithoutDetectionResult() {
    List<Object[]> recentEmails = emailRepository.findRecentEmailsWithoutDetectionResult();
    List<Map<String, Object>> emailData = recentEmails.stream().map(email -> {
      Map<String, Object> emailInfo = new HashMap<>();
      emailInfo.put("emailNo", email[0]);
      emailInfo.put("sender", email[1]);
      emailInfo.put("subject", email[2]);
      emailInfo.put("receivedDate", email[3]);
      emailInfo.put("processStatus", email[4]);
      emailInfo.put("processResult", email[5]);
      emailInfo.put("riskLevel", email[6]);
      emailInfo.put("riskDetail", email[7]);
      emailInfo.put("detectionDate", email[8]);
      emailInfo.put("recipients", email[9]);
      emailInfo.put("attachments", email[10]);
      return emailInfo;
    }).collect(Collectors.toList());

    LocalDateTime lastUpdateTime = LocalDateTime.now();
    Map<String, Object> data = new HashMap<>();
    data.put("emails", emailData);
    data.put("lastUpdateTime", lastUpdateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    Map<String, Object> response = new HashMap<>();
    response.put("status", "OK");
    response.put("message", "이메일 목록 조회 성공");
    response.put("data", data);

    return response;
  }
}
