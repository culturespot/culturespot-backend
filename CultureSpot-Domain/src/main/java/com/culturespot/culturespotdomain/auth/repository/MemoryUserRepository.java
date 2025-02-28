package com.culturespot.culturespotdomain.auth.repository;

import com.culturespot.culturespotdomain.auth.entity.User;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryUserRepository implements UserRepository {

  private final Map<String, User> userStore = new ConcurrentHashMap<>(); // 메모리 저장소

  public Optional<User> findByEmail(String email) {
    return Optional.ofNullable(userStore.get(email));
  }

  public Optional<User> findByUsername(String username) {
    return Optional.ofNullable(userStore.get(username));
  }

  public void save(User user) {
    System.out.println("email : " + user.getEmail());
    userStore.put(user.getEmail(), user);
  }

  public void clearStore() {
    userStore.clear();
  }
}