package com.wingman.service;

import com.wingman.dto.SignUpRequest;
import com.wingman.entity.User;
import com.wingman.repository.UserRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final FileService fileService;

  public User findById(Long id) {
    return userRepository.findById(id).orElseThrow();
  }

  @Transactional
  public void join(SignUpRequest request) {
    String profileImageUrl = fileService.upload(request.getProfileImage());
    String password = passwordEncoder.encode(request.getPassword());
    User user = User.builder()
        .username(request.getUsername())
        .password(password)
        .phoneNumber(request.getPhoneNumber())
        .name(request.getName())
        .gender(request.getGender())
        .birthDate(request.getBirthDate())
        .job(request.getJob())
        .region(request.getRegion())
        .profileImageUrl(profileImageUrl)
        .build();
    userRepository.save(user);
  }

}
