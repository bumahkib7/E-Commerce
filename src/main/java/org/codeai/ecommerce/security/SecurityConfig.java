package org.codeai.ecommerce.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

  private final UserDetailsService userDetailsService;

  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
          .requestMatchers("/api/admin/").hasRole("ADMIN")
          .requestMatchers("/api/customer/").hasRole("CUSTOMER")
          .requestMatchers("/api/**").permitAll()
          .requestMatchers("/").permitAll()
          .anyRequest().authenticated()
      )
      .formLogin()
      .and()
      .rememberMe()
      .rememberMeServices(rememberMeServices());

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public RememberMeServices rememberMeServices() {
    TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("myKey", userDetailsService);
    rememberMeServices.setTokenValiditySeconds(3600); // 1 hour
    return rememberMeServices;
  }

}
