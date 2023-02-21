package org.codeai.ecommerce.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
    return http
      .authorizeHttpRequests(authorizeRequests ->
        authorizeRequests
          .requestMatchers("/admin/**").hasRole("ADMIN")
          .requestMatchers("/customer/**").hasRole("CUSTOMER")
          .requestMatchers("/").permitAll()
          .anyRequest().authenticated()
      )
      .formLogin(withDefaults())
      .rememberMe(rememberMe -> rememberMe
        .rememberMeServices(rememberMeServices)
      )
      .build();

  }

  @Bean
  DefaultSecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
    HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
    requestCache.setMatchingRequestParameterName("continue");
    http
      // ...
      .requestCache((cache) -> cache
        .requestCache(requestCache)
      );
    return http.build();
  }

    @Bean
    public PasswordEncoder passwordEncoder () {
      return new BCryptPasswordEncoder();
    }

    @Bean
    RememberMeServices rememberMeServices (UserDetailsService userDetailsService){
      TokenBasedRememberMeServices.RememberMeTokenAlgorithm encodingAlgorithm = TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256;
      String myKey = userDetailsService.getClass().getName();
      TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(myKey, userDetailsService, encodingAlgorithm);
      rememberMe.setMatchingAlgorithm(TokenBasedRememberMeServices.RememberMeTokenAlgorithm.SHA256);
      return rememberMe;
    }
  }
