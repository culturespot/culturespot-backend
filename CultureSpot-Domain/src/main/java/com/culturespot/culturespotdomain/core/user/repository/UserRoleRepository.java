package com.culturespot.culturespotdomain.core.user.repository;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotdomain.core.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);
}
