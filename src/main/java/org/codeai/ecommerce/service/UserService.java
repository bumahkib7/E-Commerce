package org.codeai.ecommerce.service;

import org.codeai.ecommerce.models.User;
import org.codeai.ecommerce.repository.UserRepository;
import org.codeai.ecommerce.validator.UserValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(User user) {
    validateUser(user);
    return userRepository.save(user);

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


  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new RuntimeException("User not found");
    }
    userRepository.deleteById(id);
  }


  public void validateUser(User user) {
    UserValidator validator = new UserValidator();
    validator.validate(user);

  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = (User) userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    return new org.springframework.security.core.userdetails.User(
      user.getUsername(),
      user.getPassword(),
      user.getAuthorities());
  }
}
