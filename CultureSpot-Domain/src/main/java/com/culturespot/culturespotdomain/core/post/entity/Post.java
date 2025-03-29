package com.culturespot.culturespotdomain.core.post.entity;

import com.culturespot.culturespotdomain.core.global.entity.BaseEntity;
import com.culturespot.culturespotdomain.core.image.entity.Image;
import com.culturespot.culturespotdomain.core.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    // ************************ column ************************ //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Setter
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long viewCount = 0L;

    // ************************ column ************************ //

    @Builder
    public Post(User user, List<Image> images, String title, String content) {
        this.user = user;
        this.images = (images != null) ? images : new ArrayList<>(); // 🚀 null 방지
        this.title = title;
        this.content = content;
    }
}

