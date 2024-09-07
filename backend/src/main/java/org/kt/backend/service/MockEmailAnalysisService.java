package org.kt.backend.service;

import java.util.Random;
import org.kt.backend.dto.EmailAnalysisRequestDTO;
import org.kt.backend.dto.EmailAnalysisResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MockEmailAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(MockEmailAnalysisService.class);
    private static final int RISK_THRESHOLD = 70;
    private static final String[] RISK_TYPES = {"피싱", "멀웨어", "랜섬웨어", "스팸"};
    private static final String NO_RISK_TYPE = "유형없음";
    private static final int MIN_SLEEP_TIME = 2000;
    private static final int MAX_SLEEP_TIME = 4000;

    // 👉 - 이메일 분석 요청을 받아 분석 결과를 반환
    public EmailAnalysisResponseDTO analyzeEmail(EmailAnalysisRequestDTO requestDTO) {
        Random random = new Random();
        int riskScore = generateRiskScore(random);
        String riskType = determineRiskType(random, riskScore);
        simulateAnalysisDelay(random);
        return new EmailAnalysisResponseDTO(riskType, riskScore);
    }

    // 👉 - 랜덤 위험 점수 생성
    private int generateRiskScore(Random random) {
        return random.nextInt(100) + 1;
    }

    // 👉 - 위험 점수에 따라 위험 유형 결정
    private String determineRiskType(Random random, int riskScore) {
        if (riskScore > RISK_THRESHOLD) {
            return RISK_TYPES[random.nextInt(RISK_TYPES.length)];
        } else {
            return NO_RISK_TYPE;
        }
    }

    // 👉 - 분석 지연 시뮬레이션
    private void simulateAnalysisDelay(Random random) {
        try {
            Thread.sleep(random.nextInt(MAX_SLEEP_TIME - MIN_SLEEP_TIME) + MIN_SLEEP_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("AI 분석 시뮬레이션 중 인터럽트 발생: {}", e.getMessage());
        }
    }
}
