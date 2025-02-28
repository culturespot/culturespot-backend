package com.culturespot.culturespotdomain.auth.repository;

import com.culturespot.culturespotdomain.auth.entity.User;
import java.util.Optional;

public interface UserRepository {
  Optional<User> findByEmail(String email);
  Optional<User> findByUsername(String username);
  void save(User user);
//  void clearStore();
}
