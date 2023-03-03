package org.codeai.ecommerce.controllers;

import lombok.extern.slf4j.Slf4j;
import org.codeai.ecommerce.models.User;
import org.codeai.ecommerce.requests.LoginRequest;
import org.codeai.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

  private final UserService userService;


  public UserController(UserService userService) {
    this.userService = userService;
  }


  @PostMapping("/login")
  public ResponseEntity<?> login(LoginRequest loginRequest) {
    log.info("Logging in user: {}", loginRequest);
    User user = userService.login(loginRequest);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(User user) {
    log.info("Registering user: {}", user);
    Long registeringUser = userService.createUser(user);

    boolean isExists = userService.isUserExists(registeringUser);
    log.info("Checking if user exists: {}", isExists);

    if (isExists) {
      log.warn("User already exists: {}", registeringUser);
      return ResponseEntity.badRequest().body("User already exists");
    }
    log.info("User registered: {}", registeringUser);
    return ResponseEntity.ok(registeringUser);
  }


}
