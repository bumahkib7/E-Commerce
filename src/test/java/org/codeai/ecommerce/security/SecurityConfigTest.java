package org.codeai.ecommerce.security;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class SecurityConfigTest {
  @Autowired
  private SecurityConfig securityConfig;

  /**
   * Method under test: {@link SecurityConfig#configure(HttpSecurity)}
   */
  @Test
  @Disabled("TODO: Complete this test")
  void testConfigure() throws Exception {


    // Arrange
    AuthenticationManagerBuilder authenticationBuilder = new AuthenticationManagerBuilder(null);
    HttpSecurity http = new HttpSecurity(null, authenticationBuilder, new HashMap<>());

    // Act
    securityConfig.configure(http);
  }

  /**
   * Method under test: {@link SecurityConfig#configure(HttpSecurity)}
   */
  @Test
  @Disabled("TODO: Complete this test")
  void testConfigure2() throws Exception {
    // TODO: Complete this test.
    //   Reason: R013 No inputs found that don't throw a trivial exception.
    //   Diffblue Cover tried to run the arrange/act section, but the method under
    //   test threw
    //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.config.annotation.web.builders.HttpSecurity.authorizeHttpRequests(org.springframework.security.config.Customizer)" because "http" is null
    //       at org.codeai.ecommerce.security.SecurityConfig.configure(SecurityConfig.java:22)
    //   See https://diff.blue/R013 to resolve this issue.

    // Arrange
    HttpSecurity http = null;

    // Act
    securityConfig.configure(http);
  }

  /**
   * Method under test: {@link SecurityConfig#passwordEncoder()}
   */
  @Test
  void testPasswordEncoder() {
    // Arrange and Act
    PasswordEncoder actualPasswordEncoderResult = securityConfig.passwordEncoder();

    // Assert
    assertTrue(actualPasswordEncoderResult instanceof BCryptPasswordEncoder);
  }

  /**
   * Method under test: {@link SecurityConfig#rememberMeServices()}
   */
  @Test
  void testRememberMeServices() {
    // Arrange and Act
    RememberMeServices actualRememberMeServicesResult = securityConfig.rememberMeServices();

    // Assert
    assertTrue(actualRememberMeServicesResult instanceof TokenBasedRememberMeServices);
  }
}

