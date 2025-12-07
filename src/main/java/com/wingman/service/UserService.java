package com.wingman.service;

import com.wingman.dto.SignUpRequest;
import com.wingman.entity.User;
import com.wingman.repository.UserRepository;
import java.util.Collections;
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
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final FileService fileService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username).orElseThrow();

    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
        .build();
  }

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

  @Transactional
  public void deleteById(Long id) {
    User user = userRepository.findById(id).orElseThrow();

    if (user.getProfileImageUrl() != null) {
      fileService.delete(user.getProfileImageUrl());
    }

    userRepository.deleteById(id);
  }

}
