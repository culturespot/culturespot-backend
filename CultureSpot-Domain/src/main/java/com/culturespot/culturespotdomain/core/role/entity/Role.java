package com.culturespot.culturespotdomain.core.role.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Role {
    // ************************ column ************************ //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name="roleType", nullable = false, unique = true)
    private UserRoleType roleType;
    // ************************ column ************************ //

    @Builder
    public Role(UserRoleType roleType) {
        this.roleType = roleType;
    }
}