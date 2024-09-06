package org.kt.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "email")
public class Email {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "email_no")
  private Integer emailNo;

  @Column(name = "message_no")
  private String messageNo;

  @Column(name = "sender")
  private String sender;

  @Column(name = "subject")
  private String subject;

  @Column(name = "received_date")
  private LocalDateTime receivedDate;

  @Column(name = "process_status")
  private String processStatus;

  @Column(name = "process_result")
  private String processResult;

  @OneToMany(mappedBy = "email", cascade = CascadeType.ALL)
  private List<EmailRecipient> recipients;

  @OneToOne(mappedBy = "email", cascade = CascadeType.ALL, optional = true)
  private EmailContent content;

  @OneToMany(mappedBy = "email", cascade = CascadeType.ALL)
  private List<EmailAttachment> attachments = new ArrayList<>();

  @OneToOne(mappedBy = "email", cascade = CascadeType.ALL, optional = true)
  private EmailRisk riskLevel;

  public void addAttachment(EmailAttachment attachment) {
    attachments.add(attachment);
    attachment.setEmail(this);
  }
}
