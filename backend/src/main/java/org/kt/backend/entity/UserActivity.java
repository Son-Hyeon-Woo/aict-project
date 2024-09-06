package org.kt.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_activity")
public class UserActivity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "activity_no")
  private Integer activityNo;

  @ManyToOne
  @JoinColumn(name = "user_no", nullable = false)
  private User user;

  @Column(name = "log_date")
  private LocalDateTime logDate;

  @Column(name = "activity_type")
  private Integer activityType;

  @Column(name = "activity_detail", columnDefinition = "TEXT")
  private String activityDetail;
}
