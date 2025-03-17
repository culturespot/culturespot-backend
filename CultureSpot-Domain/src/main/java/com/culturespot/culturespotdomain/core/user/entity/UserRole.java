package com.culturespot.culturespotdomain.core.user.entity;

import com.culturespot.culturespotdomain.core.role.entity.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserRole {
    // ************************ column ************************ //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    // ************************ column ************************ //

    @Builder
    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
