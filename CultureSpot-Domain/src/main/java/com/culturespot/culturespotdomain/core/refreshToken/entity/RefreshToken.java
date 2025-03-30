package com.culturespot.culturespotdomain.core.refreshToken.entity;

import com.culturespot.culturespotdomain.core.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class RefreshToken {
    // ************************ column ************************ //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="userId", nullable = false)
    private User user;

    @Column(length = 512, nullable = false)
    private String refreshToken;

    @CreatedDate
    @Column(updatable = false, nullable = false)  // 생성일 수정 불가
    private LocalDateTime createdAt;
    // ************************ column ************************ //

    @Builder
    public RefreshToken(User user, String refreshToken) {
        this.user = user;
        this.refreshToken = refreshToken;
    }
}
