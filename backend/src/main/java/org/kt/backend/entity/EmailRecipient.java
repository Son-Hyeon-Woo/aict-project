package org.kt.backend.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "email_recipient")
public class EmailRecipient {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipient_no")
  private Integer recipientNo;

  @ManyToOne
  @JoinColumn(name = "email_no", nullable = false)
  private Email email;

  @Column(name = "recipient_email")
  private String recipientEmail;

  @Column(name = "recipient_type")
  private String recipientType;

}
