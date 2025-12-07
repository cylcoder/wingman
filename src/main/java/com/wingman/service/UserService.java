package com.wingman.service;

import com.wingman.dto.SignUpRequest;
import com.wingman.entity.Gender;
import com.wingman.entity.User;
import com.wingman.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

  public List<User> findRandomUsers(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow();

    Gender oppositeGender = (user.getGender() == Gender.MALE) ? Gender.FEMALE : Gender.MALE;
    return userRepository.findRandomUsersByGender(oppositeGender);
  }

  @Transactional
  public void deleteById(Long id) {
    User user = userRepository.findById(id).orElseThrow();

    if (user.getProfileImageUrl() != null) {
      fileService.delete(user.getProfileImageUrl());
    }

    userRepository.deleteById(id);
  }

}
