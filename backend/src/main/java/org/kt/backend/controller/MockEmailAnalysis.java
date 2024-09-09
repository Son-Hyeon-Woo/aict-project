package org.kt.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.kt.backend.dto.EmailAnalysisRequestDTO;
import org.kt.backend.dto.EmailAnalysisResponseDTO;
import org.kt.backend.service.EmailAnalysisAPI;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 이메일 분석을 위한 모의 컨트롤러 이 클래스는 이메일 분석 요청을 처리하고 결과를 반환합니다.
 */
@RestController
@RequestMapping("/mock-api")
public class MockEmailAnalysis {

  /**
   * 이메일 분석 서비스
   */
  private final EmailAnalysisAPI emailAnalysisAPI;

  /**
   * 컨트롤러 생성자
   *
   * @param mockEmailAnalysisService 주입될 이메일 분석 서비스
   */
  // @Autowired
  public MockEmailAnalysis(EmailAnalysisAPI emailAnalysisAPI) {
    this.emailAnalysisAPI = emailAnalysisAPI;
  }

  /**
   * 이메일 분석 요청을 처리하는 메서드
   *
   * @param request 이메일 분석 요청 데이터
   * @return 이메일 분석 결과
   */
  @PostMapping("/email-analysis")
  @Operation(summary = "이메일 분석", description = "이메일 분석 요청을 처리하고 결과를 반환합니다.")
  public EmailAnalysisResponseDTO analyzeEmail(@RequestBody EmailAnalysisRequestDTO request) {
    return emailAnalysisAPI.analyzeEmail(request);
  }

  @PostMapping("/email-analysis2")
  @Operation(summary = "이메일 분석2", description = "이메일 분석 요청을 처리하고 결과를 반환합니다.")
  public List<EmailAnalysisResponseDTO> analyzeEmail2(@RequestBody List<EmailAnalysisRequestDTO> emails) {
    return emailAnalysisAPI.analyzeEmails(emails);
  }
}