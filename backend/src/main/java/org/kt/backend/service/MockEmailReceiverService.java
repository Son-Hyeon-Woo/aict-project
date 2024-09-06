/* MockEmailReceiverService
 *  `receiveMockEmails` 을 주기적으로 실행
 *   receiveMockEmails 에서 1~5 사이의 이메일을 생성한다. (createMockEmail)
 * */

package org.kt.backend.service;

import java.util.Random;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
import java.util.UUID;

import java.time.LocalDateTime;

import org.kt.backend.dto.EmailDTO;
import org.kt.backend.dto.EmailRecipientDTO;
import org.kt.backend.dto.EmailAttachmentDTO;
import org.kt.backend.dto.EmailContentDTO;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MockEmailReceiverService {
  private final EmailService emailService;
  private final Random random = new Random();

  //NOTE - 생성자가 하나인 경우 @Autowired 생략 가능
  public MockEmailReceiverService(EmailService  emailService) {
    this.emailService = emailService;
  }

  @Scheduled(fixedRate = 30000) //* 일정 주기마다 실행
  public void receiveMockEmails() {
    int emailCount = random.nextInt(5) + 1; // 1~5개의 이메일 생성
    for (int i = 0; i < emailCount; i++) {
      EmailDTO emailDTO = createMockEmail();
      emailService.saveEmail(emailDTO);
    }
    System.out.println("Finished generating mock emails. Total count: " + emailCount);
  }

  private EmailDTO createMockEmail() {
    EmailDTO emailDTO = new EmailDTO();
    emailDTO.setMessageNo(UUID.randomUUID().toString());
    emailDTO.setSubject(generateRandomSubject());
    emailDTO.setReceivedDate(LocalDateTime.now());
    emailDTO.setProcessStatus("진행전");
    emailDTO.setProcessResult("처리전");
    emailDTO.setSender(createRandomSender());
    emailDTO.setRecipients(createRandomRecipients());
    EmailContentDTO content = new EmailContentDTO();
    content.setContent(generateRandomEmailContent());
    emailDTO.setContent(content);
    emailDTO.setRiskLevel(null);
    emailDTO.setAttachments(createRandomAttachments());
    return emailDTO;
  }

  // 랜덤 발신자 생성
  private String createRandomSender() {
    String[] domains = {"example.com", "mail.com", "test.org", "sample.net"};
    String username = generateRandomUsername();
    String domain = domains[random.nextInt(domains.length)];
    return username + "@" + domain;
  }

  // 랜덤 사용자 이름 생성
  private String generateRandomUsername() {
  String characters = "abcdefghijklmnopqrstuvwxyz";
  int length = random.nextInt(5) + 5; // 5~9자의 길이
  StringBuilder username = new StringBuilder(length);
  for (int i = 0; i < length; i++) {
      username.append(characters.charAt(random.nextInt(characters.length())));
  }
  return username.toString();
}

  // 랜덤 수신자 생성
  private List<EmailRecipientDTO> createRandomRecipients() {
    int recipientCount = random.nextInt(5) + 1; // 1~5명의 수신자 생성
    List<EmailRecipientDTO> recipients = new ArrayList<>();

    for (int i = 0; i < recipientCount; i++) {
      EmailRecipientDTO recipient = new EmailRecipientDTO();
      recipient.setRecipientEmail(generateRandomUsername() + "@example.com");
      recipient.setRecipientType(getRandomRecipientType());
      recipients.add(recipient);
    }

    return recipients;
  }
  // 랜덤 수신자 타입 생성
  private String getRandomRecipientType() {
    String[] types = {"TO", "CC", "BCC"};
    return types[random.nextInt(types.length)];
  }

  // 랜덤 이메일 제목 생성
  private String generateRandomSubject() {
    String[] subjects = {
        "회의 일정 안내", "프로젝트 진행 상황 보고", "휴가 신청 승인 요청",
        "신규 서비스 출시 안내", "보안 업데이트 공지", "월간 실적 보고서",
        "고객 피드백 요약", "팀 빌딩 이벤트 안내", "시스템 점검 예정 공지"
    };
    return subjects[random.nextInt(subjects.length)];
  }

  // 랜덤 이메일 내용 생성
  private String generateRandomEmailContent() {
    int paragraphCount = random.nextInt(3) + 1;
    return IntStream.range(0, paragraphCount)
        .mapToObj(i -> generateRandomParagraph())
        .collect(Collectors.joining("\n\n"));
  }

  //  랜덤 단락 생성
  private String generateRandomParagraph() {
    int sentenceCount = random.nextInt(4) + 2;
    return IntStream.range(0, sentenceCount)
        .mapToObj(i -> generateRandomSentence())
        .collect(Collectors.joining(" "));
  }

  // 랜덤 문장 생성
  private String generateRandomSentence() {
    String[] starts = {"안녕하세요,", "알려드립니다.", "확인 부탁드립니다.", "주의해 주시기 바랍니다."};
    String[] middles = {"프로젝트가 순조롭게 진행되고 있습니다.", "회의 일정이 변경되었습니다.", "새로운 정책이 시행될 예정입니다."};
    String[] ends = {"감사합니다.", "문의사항이 있으면 연락주세요.", "좋은 하루 보내세요."};

    return starts[random.nextInt(starts.length)] + " " +
        middles[random.nextInt(middles.length)] + " " +
        ends[random.nextInt(ends.length)];
  }

  // 첨부 파일 추가
  private List<EmailAttachmentDTO> createRandomAttachments() {
    List<EmailAttachmentDTO> attachments = new ArrayList<>();
    int attachmentCount = random.nextInt(4); // 0~3개의 첨부파일

    for (int i = 0; i < attachmentCount; i++) {
      EmailAttachmentDTO attachment = new EmailAttachmentDTO();
      attachment.setFileName(generateRandomFileName());
      attachment.setFileSize(random.nextInt(10 * 1024 * 1024)); // 최대 10MB
      attachment.setFilePath("/mock/attachments/" + UUID.randomUUID().toString());
      attachments.add(attachment);
    }

    return attachments;
  }



  // 랜덤 파일 이름 생성
  private String generateRandomFileName() {
    String[] fileTypes = {".pdf", ".docx", ".xlsx", ".jpg", ".png", ".zip"};
    String[] fileNames = {"report", "document", "image", "data", "presentation"};
    return fileNames[random.nextInt(fileNames.length)] + "_" +
        random.nextInt(1000) +
        fileTypes[random.nextInt(fileTypes.length)];
  }

}