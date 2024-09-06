package org.kt.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "setting")
public class Setting {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "setting_no")
  private Integer settingNo;

  @Column(name = "setting_key")
  private String settingKey;

  @Column(name = "setting_value")
  private String settingValue;

  @Column(name = "setting_description", columnDefinition = "TEXT")
  private String settingDescription;

  @Column(name = "last_update")
  private LocalDateTime lastUpdate;
}
