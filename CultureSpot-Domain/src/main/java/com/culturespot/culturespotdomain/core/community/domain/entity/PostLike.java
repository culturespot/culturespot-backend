package com.culturespot.culturespotdomain.core.community.domain.entity;

import com.culturespot.culturespotdomain.core.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "community_likes")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public void assignPost(Post post) {
        this.post = post;
    }
}
