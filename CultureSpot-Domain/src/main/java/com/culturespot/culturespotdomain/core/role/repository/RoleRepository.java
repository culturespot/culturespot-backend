package com.culturespot.culturespotdomain.core.role.repository;

import com.culturespot.culturespotdomain.core.role.entity.Role;
import com.culturespot.culturespotdomain.core.role.entity.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(UserRoleType roleType);
}