package com.culturespot.culturespotdomain.auth.entity;

import com.culturespot.culturespotdomain.auth.enums.OAuthProvider;
import com.culturespot.culturespotdomain.auth.enums.UserRole;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(name = "unique_email", columnNames = "email"),
    @UniqueConstraint(name = "unique_username", columnNames = "username") // 유저네임도 중복 방지
})
@EntityListeners(AuditingEntityListener.class)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 40, unique = true)
  private String email;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(unique = true, nullable = false, length = 32)
  private String username;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OAuthProvider oAuthProvider;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private UserRole userRoles;

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Builder
  public User(String email, String password, String username, OAuthProvider oAuthProvider, UserRole userRoles) {
    this.email = email;
    this.password = password;
    this.username = username;
    this.oAuthProvider = oAuthProvider;
    this.userRoles = userRoles;
    this.createdAt = LocalDateTime.now();
  }

//  public void changePassword(String newPassword) {
//    this.password = newPassword;
//  }
}
