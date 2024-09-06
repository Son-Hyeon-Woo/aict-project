package org.kt.backend.controller;

import org.kt.backend.dto.EmailAnalysisRequestDTO;
import org.kt.backend.dto.EmailAnalysisResponseDTO;
import org.kt.backend.service.MockEmailAnalysisService;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 이메일 분석을 위한 모의 컨트롤러
 * 이 클래스는 이메일 분석 요청을 처리하고 결과를 반환합니다.
 */
@RestController
@RequestMapping("/mock-api/email-analysis")
public class MockEmailAnalysisController {

    /**
     * 이메일 분석 서비스
     */
    private final MockEmailAnalysisService mockEmailAnalysisService;

    /**
     * 컨트롤러 생성자
     * @param mockEmailAnalysisService 주입될 이메일 분석 서비스
     */
    // @Autowired
    public MockEmailAnalysisController(MockEmailAnalysisService mockEmailAnalysisService) {
        this.mockEmailAnalysisService = mockEmailAnalysisService;
    }

    /**
     * 이메일 분석 요청을 처리하는 메서드
     * @param request 이메일 분석 요청 데이터
     * @return 이메일 분석 결과
     */
    @PostMapping
    public EmailAnalysisResponseDTO analyzeEmail(@RequestBody EmailAnalysisRequestDTO request) {
        return mockEmailAnalysisService.analyzeEmail(request);
    }
}