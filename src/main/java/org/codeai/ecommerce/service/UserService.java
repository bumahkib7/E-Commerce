package org.codeai.ecommerce.service;

import org.codeai.ecommerce.exceptions.UserValidationException;
import org.codeai.ecommerce.models.User;
import org.codeai.ecommerce.repository.UserRepository;
import org.codeai.ecommerce.requests.LoginRequest;
import org.codeai.ecommerce.validator.UserValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  private static  volatile String  MY_LOGGER_NAME = "User.logger";
  private final Logger logger = Logger.getLogger(MY_LOGGER_NAME,UserService.class.getName());

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Long createUser(User user) {
    validateUser(user);
    return userRepository.save(user).getId();

  }

  public User getUserById(Long id) {
    if (!userRepository.existsById(id)) {
      throw new RuntimeException("User not found");
    }
    return userRepository.findById(id).orElse(null);
  }


  public User updateUser(User user) {
    validateUser(user);
    if (userRepository.existsById(user.getId())) {
      return userRepository.findById(user.getId()).map(u -> {
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        return userRepository.save(u);
      }).orElse(null);
    }
    return user;
  }

  public User login(LoginRequest loginRequest) {
    return (User) userRepository.findByUsernameAndPassword(loginRequest.username(), loginRequest.password())
      .orElse(null);
  }

  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new RuntimeException("User not found");
    }
    userRepository.deleteById(id);
  }

  public boolean isUserExists(Long user) {
    return userRepository.IsExists(user);
  }

  public void validateUser(User user) {
    try {
      UserValidator userValidator = new UserValidator();
      if (user.getId() != null) {
        userValidator.validateUserAlreadyExists(user);
      } else {
        userValidator.validate(user);
      }
    } catch (UserValidationException ignored) {
      logger.info("User is not valid");
    }

  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    return new org.springframework.security.core.userdetails.User(
      user.getUsername(),
      user.getPassword(),
      user.getAuthorities());
  }


}
