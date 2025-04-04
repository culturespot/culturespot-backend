package com.culturespot.culturespotdomain.core.user.entity;

import com.culturespot.culturespotdomain.core.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {
    // ************************ column ************************ //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(name="profile_code", nullable = false)
    private int profileCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialLoginType authType;

    @Column(name="last_login_at", nullable = false)
    private LocalDateTime lastLoginAt;
    // ************************ column ************************ //

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserRole> roles = new HashSet<>();

    @Builder
    public User(
            String email,
            String nickname,
            SocialLoginType authType,
            Set<UserRole> roles,
            String password,
            int profileCode,
            LocalDateTime lastLoginAt
    ){
        this.email = email;
        this.nickname = nickname;
        this.authType = authType;
        this.roles = roles;
        this.password = password;
        this.profileCode = profileCode;
        this.lastLoginAt = lastLoginAt;
    }

    public void addRole(UserRole userRole) {
        this.roles.add(userRole);
        userRole.setUser(this);
    }
}