package com.culturespot.culturespotdomain.core.community.domain.entity;

import com.culturespot.culturespotdomain.common.BaseEntity;
import com.culturespot.culturespotdomain.core.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
@Table(name = "community_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Setter
    @Column(nullable = false)
    private Long viewCount = 0L;

    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    List<PostComment> postComments;

    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    List<PostImage> postImages;

    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    List<PostLike> postLikes;


    @Builder
    public Post(User user, String title, String content, List<PostImage> postImages) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.postImages = postImages;
    }

    public void addComment(PostComment comment) {
        postComments.add(comment);
        comment.assignPost(this);
    }

    public void addComments(List<PostComment> comments) {
        for(PostComment comment: comments) {
            addComment(comment);
        }
    }

    public void addImage(PostImage postImage) {
        this.postImages.add(postImage);
        postImage.assignPost(this);
    }

    public void addImages(List<PostImage> postImages) {
        for (PostImage postImage : postImages) {
            addImage(postImage);
        }
    }

    public void addLike(PostLike postLike) {
        postLikes.add(postLike);
        postLike.assignPost(this);
    }

    public void addLikes(List<PostLike> postLikes) {
        for(PostLike postLike : postLikes) {
            addLike(postLike);
        }
    }
}
