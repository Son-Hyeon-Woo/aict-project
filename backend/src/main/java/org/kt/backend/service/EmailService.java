package org.kt.backend.service;

import jakarta.transaction.Transactional;
import org.kt.backend.dto.EmailDTO;
import org.kt.backend.entity.Email;
import org.kt.backend.entity.EmailRecipient;
import org.kt.backend.entity.EmailAttachment;
import org.kt.backend.repository.EmailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * 이메일 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class EmailService {
  /** 이메일 데이터에 접근하기 위한 리포지토리 */
  private final EmailRepository emailRepository;
  
  /** DTO와 엔티티 간 매핑을 위한 ModelMapper */
  private final ModelMapper modelMapper;

  /**
   * EmailService 생성자
   * @param emailRepository 이메일 리포지토리
   * @param modelMapper ModelMapper 인스턴스
   */
  public EmailService(EmailRepository emailRepository, ModelMapper modelMapper) {
    this.emailRepository = emailRepository;
    this.modelMapper = modelMapper;
  }

  /**
   * 이메일 DTO를 받아 데이터베이스에 저장합니다.
   * @param emailDTO 저장할 이메일 정보가 담긴 DTO
   */
  @Transactional
  public void saveEmail(EmailDTO emailDTO) {
    // DTO를 엔티티로 변환
    Email email = modelMapper.map(emailDTO, Email.class);
    
    // EmailRecipient의 email 필드를 설정
    if (email.getRecipients() != null) {
        for (EmailRecipient recipient : email.getRecipients()) {
            recipient.setEmail(email);
        }
    }

    // EmailContent의 email 필드를 설정
    if (email.getContent() != null) {
        email.getContent().setEmail(email);
    }

    // EmailAttachment의 email 필드를 설정
    if (email.getAttachments() != null) {
        for (EmailAttachment attachment : email.getAttachments()) {
            attachment.setEmail(email);
        }
    }

    // EmailRisk의 email 필드를 설정
    if (email.getRiskLevel() != null) {
        email.getRiskLevel().setEmail(email);
    }

    
    emailRepository.save(email);
  }

  /**
   * 이메일 엔티티를 DTO로 변환합니다.
   * @param email 변환할 이메일 엔티티
   * @return 변환된 이메일 DTO
   */
  public EmailDTO convertToDto(Email email) {
    return modelMapper.map(email, EmailDTO.class);
  }

  /**
   * 이메일 DTO를 엔티티로 변환합니다.
   * @param emailDTO 변환할 이메일 DTO
   * @return 변환된 이메일 엔티티
   */
  public Email convertToEntity(EmailDTO emailDTO) {
    return modelMapper.map(emailDTO, Email.class);
  }
}