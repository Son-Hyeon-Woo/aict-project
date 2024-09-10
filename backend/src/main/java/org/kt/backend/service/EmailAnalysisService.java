package org.kt.backend.service;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

import org.kt.backend.dto.EmailAnalysisRequestDTO;
import org.kt.backend.dto.EmailAnalysisResponseDTO;
import org.kt.backend.dto.EmailRiskDTO;
import org.kt.backend.entity.Email;
import org.kt.backend.entity.EmailAttachment;
import org.kt.backend.entity.EmailRisk;
import org.kt.backend.repository.EmailRepository;
import org.kt.backend.repository.EmailRiskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.ArrayList;

@Slf4j
@Service
public class EmailAnalysisService {

  @Autowired
  private EmailRepository emailRepository;

  @Autowired
  private EmailRiskRepository emailRiskRepository;

  @Autowired
  private WebClient webClient;

  // @Scheduled(fixedRate = 3000) // 10초마다 실행
  // @Transactional
  // public void analyzePendingEmail() {
  // log.info("진행전 상태인 이메일 가져오기..");
  // Optional<Email> optionalEmail =
  // emailRepository.findFirstByProcessStatusOrderByReceivedDateAsc("진행전");
  // if (optionalEmail.isPresent()) {
  // analyzeEmail(optionalEmail.get());
  // }
  // }

  @Scheduled(fixedRate = 2000) // 10초마다 실행
  @Transactional
  public void analyzePendingEmails() {
    log.info("진행전 상태인 이메일들 가져오기..");
    Pageable pageable = PageRequest.of(0, 2);
    List<Email> pendingEmails = emailRepository.findByProcessStatusOrderByReceivedDateAsc("진행전", pageable);
    if (!pendingEmails.isEmpty()) {
      analyzeEmails(pendingEmails);
    }
  }

  @Transactional
  public void analyzeEmail(Email email) {
    // 👉 - 이메일 분석 요청 DTO 생성
    EmailAnalysisRequestDTO requestDTO = new EmailAnalysisRequestDTO(email.getSender(), email.getContent().getContent(),
        email.getAttachments().isEmpty() ? null
            : email.getAttachments().stream().map(EmailAttachment::getFilePath)
                .collect(java.util.stream.Collectors.toList()));

    // 👉 - 이메일 분석 API 호출 및 응답 수신
    log.info("메일 분석 API로 요청..");

    EmailAnalysisResponseDTO responseDTO = webClient.post().uri("http://localhost:8080/mock-api/email-analysis")
        .bodyValue(requestDTO)
        .retrieve().bodyToMono(EmailAnalysisResponseDTO.class).block();

    if (responseDTO != null) {
      EmailRiskDTO riskDTO = new EmailRiskDTO();
      riskDTO.setEmailNo(email.getEmailNo());

      // * 값에 따라서 RiskLevel 다르게 적용
      int riskScore = responseDTO.getRiskScore();

      if (riskScore >= 1 && riskScore <= 20) {
        riskDTO.setRiskLevel("안전");
      } else if (riskScore > 20 && riskScore <= 70) {
        riskDTO.setRiskLevel("보통");
      } else if (riskScore > 70 && riskScore <= 85) {
        riskDTO.setRiskLevel("위험");
      } else if (riskScore > 85 && riskScore <= 100) {
        riskDTO.setRiskLevel("매우 위험");
      }

      riskDTO.setRiskDetail(responseDTO.getRiskType());
      riskDTO.setDetectionDate(LocalDateTime.now());

      EmailRisk emailRisk = new EmailRisk();
      emailRisk.setEmail(email);
      emailRisk.setRiskLevel(riskDTO.getRiskLevel());
      emailRisk.setRiskDetail(riskDTO.getRiskDetail());
      emailRisk.setDetectionDate(riskDTO.getDetectionDate());

      emailRiskRepository.save(emailRisk);

      email.setRiskLevel(emailRisk);
      email.setProcessStatus("완료");

      if (riskDTO.getRiskLevel().equals("안전") || riskDTO.getRiskLevel().equals("보통")) {
        email.setProcessResult("전송");
      } else {
        email.setProcessResult("차단");
      }

      emailRepository.save(email);
      log.info("이메일 분석 완료");
    }
  }

  @Transactional
  public void analyzeEmails(List<Email> emails) {
    List<EmailAnalysisRequestDTO> requestDTOList = new ArrayList<>();
    for (Email email : emails) {
      EmailAnalysisRequestDTO requestDTO = new EmailAnalysisRequestDTO(
          email.getSender(),
          email.getContent().getContent(),
          email.getAttachments().isEmpty() ? null
              : email.getAttachments().stream()
                  .map(EmailAttachment::getFilePath)
                  .collect(java.util.stream.Collectors.toList()));
      requestDTOList.add(requestDTO);
    }

    // 👉 - 이메일 분석 API 호출 및 응답 수신
    log.info("메일 분석 API로 요청..");

    List<EmailAnalysisResponseDTO> responseDTOs = webClient.post().uri("http://localhost:8080/mock-api/email-analysis2")
        .bodyValue(requestDTOList)
        .retrieve().bodyToFlux(EmailAnalysisResponseDTO.class).collectList().block();

    for (int i = 0; i < responseDTOs.size(); i++) {
      EmailAnalysisResponseDTO responseDTO = responseDTOs.get(i);
      Email email = emails.get(i);
      if (responseDTO != null) {
        EmailRiskDTO riskDTO = new EmailRiskDTO();
        riskDTO.setEmailNo(email.getEmailNo());

        // * 값에 따라서 RiskLevel 다르게 적용
        int riskScore = responseDTO.getRiskScore();

        if (riskScore >= 1 && riskScore <= 20) {
          riskDTO.setRiskLevel("안전");
        } else if (riskScore > 20 && riskScore <= 70) {
          riskDTO.setRiskLevel("보통");
        } else if (riskScore > 70 && riskScore <= 85) {
          riskDTO.setRiskLevel("위험");
        } else if (riskScore > 85 && riskScore <= 100) {
          riskDTO.setRiskLevel("매우 위험");
        }

        riskDTO.setRiskDetail(responseDTO.getRiskType());
        riskDTO.setDetectionDate(LocalDateTime.now());

        EmailRisk emailRisk = new EmailRisk();
        emailRisk.setEmail(email);
        emailRisk.setRiskLevel(riskDTO.getRiskLevel());
        emailRisk.setRiskDetail(riskDTO.getRiskDetail());
        emailRisk.setDetectionDate(riskDTO.getDetectionDate());

        emailRiskRepository.save(emailRisk);

        email.setRiskLevel(emailRisk);
        email.setProcessStatus("완료");

        if (riskDTO.getRiskLevel().equals("안전") || riskDTO.getRiskLevel().equals("보통")) {
          email.setProcessResult("전송");
        } else {
          email.setProcessResult("차단");
        }

        emailRepository.save(email);
      }
      log.info("이메일 분석 완료");
    }
  }
}
