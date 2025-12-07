package com.wingman.service;

import com.wingman.dto.LoginRequest;
import com.wingman.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final JwtTokenProvider provider;
  private final AuthenticationManager manager;

  public String authenticateAndGenerateToken(LoginRequest request) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        request.getUsername(), request.getPassword());

    Authentication authentication = manager.authenticate(token);

    return provider.createToken(authentication.getName());
  }

}
