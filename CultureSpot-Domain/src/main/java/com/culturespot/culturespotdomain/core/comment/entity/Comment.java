package com.culturespot.culturespotdomain.core.comment.entity;

import com.culturespot.culturespotdomain.core.global.entity.BaseEntity;
import com.culturespot.culturespotdomain.core.post.entity.Post;
import com.culturespot.culturespotdomain.core.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column
    private String content;
}