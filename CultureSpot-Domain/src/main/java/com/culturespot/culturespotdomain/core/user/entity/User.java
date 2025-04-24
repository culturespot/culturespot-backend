package com.culturespot.culturespotdomain.core.user.entity;

import com.culturespot.culturespotdomain.core.global.entity.BaseEntity;
import com.culturespot.culturespotdomain.core.global.exception.DomainException;
import com.culturespot.culturespotdomain.core.global.exception.DomainExceptionCode;
import com.culturespot.culturespotdomain.core.performance.entity.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

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

    @Setter
    @Column(nullable = false, unique = true)
    private String nickname;

    @Setter
    @Column(name="profile_code", nullable = false)
    private int profileCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialLoginType authType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name="preferred_category", columnDefinition="json")
    Map<String, List<Category>> preferredCategory = new HashMap<>();

    @Column(name="last_login_at", nullable = false)
    private LocalDateTime lastLoginAt;
    // ************************ column ************************ //

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> roles = new HashSet<>();

    @Builder
    public User(
            String email,
            String nickname,
            SocialLoginType authType,
            Set<UserRole> roles,
            int profileCode,
            LocalDateTime lastLoginAt,
            Map<String, List<Category>> preferredCategory
    ){
        this.email = email;
        this.nickname = nickname;
        this.authType = authType;
        this.roles = roles != null ? roles : new HashSet<>();
        this.profileCode = profileCode;
        this.lastLoginAt = lastLoginAt;
        this.preferredCategory = preferredCategory != null ? preferredCategory : new HashMap<>();
    }

    public void addRole(UserRole userRole) {
        this.roles.add(userRole);
        userRole.setUser(this);
    }

    public void updatePreferredCategory(List<String> categories) {
        preferredCategory.clear();
        List<Category> categoriesList = new ArrayList<>();

        for (String categoryName : categories) {
            Category category = Category.fromString(categoryName);
            categoriesList.add(category);
        }

        preferredCategory.put("select", categoriesList);
    }
}