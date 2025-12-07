package com.wingman.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class SmsVerification extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private String verificationCode;

  private boolean isVerified;

  private LocalDateTime expiresAt;

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expiresAt);
  }

  public void verify() {
    isVerified = true;
  }

}
