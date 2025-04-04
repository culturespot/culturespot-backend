package com.culturespot.culturespotdomain.core.like.entity;

import com.culturespot.culturespotdomain.core.post.entity.Post;
import com.culturespot.culturespotdomain.core.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;
}
