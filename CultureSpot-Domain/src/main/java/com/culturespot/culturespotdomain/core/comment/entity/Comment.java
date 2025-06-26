package com.culturespot.culturespotdomain.core.comment.entity;

import com.culturespot.culturespotdomain.core.community.domain.entity.Post;
import com.culturespot.culturespotdomain.core.global.entity.BaseEntity;
//import com.culturespot.culturespotdomain.core.post.entity.Post;
import com.culturespot.culturespotdomain.core.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(callSuper = true)
@Builder(toBuilder = true, access = AccessLevel.PUBLIC)
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
@Entity
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column
    private String content;

    @Builder.Default()
    @Convert(converter = LikedUserIdsConverter.class)
    @Column(name = "liked_user_ids", columnDefinition = "TEXT")
    private Set<Long> likedUserIds = new HashSet<>();

    public void updateComment(String content) {
        this.content = content;
    }

    public void incrementLikes(Long userId) {
        likedUserIds.add(userId);
    }

    public void decrementLikes(Long userId) {
        likedUserIds.remove(userId);
    }
}