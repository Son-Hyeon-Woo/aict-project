package org.kt.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import java.util.List;
import jakarta.persistence.CascadeType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "app_user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_no")
  private Integer userNo;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "id")
  private String id;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;

  @Column(name = "regist_date")
  @CreationTimestamp
  private LocalDate registDate;

  @Column(name = "last_login_date")
  private LocalDateTime lastLoginDate;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<UserActivity> activities;
}