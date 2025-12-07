package com.wingman.dto;

import com.wingman.entity.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SignUpRequest {

  private String username;
  private String password;
  private String phoneNumber;
  private String name;
  private Gender gender;
  private LocalDate birthDate;
  private String job;
  private String region;
  private MultipartFile profileImage;

}
