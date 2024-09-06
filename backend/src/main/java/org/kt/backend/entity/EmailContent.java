package org.kt.backend.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "email_content")
public class EmailContent {
  @Id
  @Column(name = "email_no")
  private Integer emailNo;

  @OneToOne
  @MapsId
  @JoinColumn(name = "email_no")
  private Email email;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

}
