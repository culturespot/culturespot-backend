package com.culturespot.culturespotdomain.auth;

import com.culturespot.culturespotdomain.auth.repository.MemoryUserRepository;
import com.culturespot.culturespotdomain.auth.repository.UserRepository;
import com.culturespot.culturespotdomain.auth.service.UserService;
import com.culturespot.culturespotdomain.auth.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

  @Bean
  public UserRepository userRepository() {
    return new MemoryUserRepository();
  }

  @Bean
  public UserService userService(PasswordEncoder passwordEncoder) {
    return new UserServiceImpl(userRepository(), passwordEncoder);
  }
}
