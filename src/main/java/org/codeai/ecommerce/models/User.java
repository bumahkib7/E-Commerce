package org.codeai.ecommerce.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codeai.ecommerce.Enums.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Roles roles;

  @Column(nullable = false)
  private boolean enabled;

  @Column(nullable = false)
  private String token;

  @Column(nullable = false)
  private String tokenExpirationDate;

  public User(Long id, String firstName, String lastName, String username, String password, String email, Roles roles, boolean enabled, String token, String tokenExpirationDate) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.email = email;
    this.roles = roles;
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
    return getRoles() == user.getRoles();
  }

  @Override
  public int hashCode() {
    int result = getId().hashCode();
    result = 31 * result + getUsername().hashCode();
    result = 31 * result + getPassword().hashCode();
    result = 31 * result + getEmail().hashCode();
    result = 31 * result + getRoles().hashCode();
    return result;
  }


  @Override
  public Set<GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + roles.toString()));
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }
}
