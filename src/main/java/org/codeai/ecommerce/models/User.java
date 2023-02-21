package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codeai.ecommerce.Enums.Roles;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private Roles role;

  @Column(nullable = false)
  private boolean enabled;

  @Column(nullable = false)
  private String token;

  @Column(nullable = false)
  private String tokenExpirationDate;

  public User(Long id, String username, String password, String email, Roles role, boolean enabled, String token, String tokenExpirationDate) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
    this.enabled = enabled;
    this.token = token;
    this.tokenExpirationDate = tokenExpirationDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User user)) return false;

    if (!getId().equals(user.getId())) return false;
    if (!getUsername().equals(user.getUsername())) return false;
    if (!getPassword().equals(user.getPassword())) return false;
    if (!getEmail().equals(user.getEmail())) return false;
    return getRole() == user.getRole();
  }

  @Override
  public int hashCode() {
    int result = getId().hashCode();
    result = 31 * result + getUsername().hashCode();
    result = 31 * result + getPassword().hashCode();
    result = 31 * result + getEmail().hashCode();
    result = 31 * result + getRole().hashCode();
    return result;
  }
}
