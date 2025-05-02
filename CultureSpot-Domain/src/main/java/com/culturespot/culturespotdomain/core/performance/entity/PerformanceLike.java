package com.culturespot.culturespotdomain.core.performance.entity;

import com.culturespot.culturespotdomain.core.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "performance_likes")
public class PerformanceLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    protected PerformanceLike() {
    }

    public PerformanceLike(User user, Performance performance) {
        this.user = user;
        this.performance = performance;
    }

}
