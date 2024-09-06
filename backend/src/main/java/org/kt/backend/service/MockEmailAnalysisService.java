package org.kt.backend.service;

import java.util.Random;
import org.kt.backend.dto.EmailAnalysisRequestDTO;
import org.kt.backend.dto.EmailAnalysisResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class MockEmailAnalysisService {

  public EmailAnalysisResponseDTO analyzeEmail(EmailAnalysisRequestDTO requestDTO) {
    Random random = new Random();

    int riskScore = random.nextInt(100) + 1;

    String[] riskTypes = {"피싱", "멀웨어", "랜섬웨어", "스팸"};
    String riskType = riskTypes[random.nextInt(riskTypes.length)];

    // AI 분석을 시뮬레이션하기 위한 대기 시간 추가
    try {
      // 2~4초 사이의 랜덤한 시간 동안 대기
      Thread.sleep(random.nextInt(2000) + 2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.err.println("AI 분석 시뮬레이션 중 인터럽트 발생: " + e.getMessage());
    }

    return new EmailAnalysisResponseDTO(riskType, riskScore);
  }
}
