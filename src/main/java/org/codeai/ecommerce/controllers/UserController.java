package org.codeai.ecommerce.controllers;

import org.codeai.ecommerce.models.User;
import org.codeai.ecommerce.requests.LoginRequest;
import org.codeai.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }


  @PostMapping("/login")
  public ResponseEntity<?> login(LoginRequest loginRequest) {
    User user = userService.login(loginRequest);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(User user) {
    User registeringUser = userService.createUser(user);
    if (registeringUser == null) {
      return ResponseEntity.badRequest().body("User already exists");
    }
    return ResponseEntity.ok(registeringUser);
  }


}
