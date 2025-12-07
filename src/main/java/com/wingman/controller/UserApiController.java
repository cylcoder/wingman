package com.wingman.controller;

import com.wingman.dto.LoginRequest;
import com.wingman.dto.SignUpRequest;
import com.wingman.entity.User;
import com.wingman.service.AuthService;
import com.wingman.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

  private final UserService userService;
  private final AuthService authService;

  @PostMapping
  public void signUp(SignUpRequest request) {
    userService.join(request);
  }

  @PostMapping("/login")
  public void login(@RequestBody LoginRequest request, HttpServletResponse response) {
    String jwt = authService.authenticateAndGenerateToken(request);
    Cookie cookie = new Cookie("jwt", jwt);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(3600);
    response.addCookie(cookie);
  }

  @PostMapping("/logout")
  public void logout(HttpServletResponse response) {
    Cookie cookie = new Cookie("jwt", null);
    cookie.setMaxAge(0);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  @GetMapping("/discover")
  public List<User> discover(@AuthenticationPrincipal UserDetails userDetails) {
    return userService.findRandomUsers(userDetails.getUsername());
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    userService.deleteById(id);
  }

}
