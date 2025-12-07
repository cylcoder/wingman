package com.wingman.controller;

import com.wingman.dto.SignUpRequest;
import com.wingman.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @PostMapping
  public void signUp(SignUpRequest request) {
    userService.join(request);
  }

}
