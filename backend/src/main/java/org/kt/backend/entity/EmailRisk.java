package org.kt.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "email_risk")
public class EmailRisk {
  @Id
  @Column(name = "email_no")
  private Integer emailNo;

  @OneToOne
  @MapsId
  @JoinColumn(name = "email_no")
  private Email email;

  @Column(name = "risk_level")
  private String riskLevel;

  @Column(name = "risk_detail", columnDefinition = "TEXT")
  private String riskDetail;

  @Column(name = "detection_date")
  private LocalDateTime detectionDate;

  // @Column(name = "detection_result")
  // private String detectionResult;

}
