package com.culturespot.culturespotdomain.core.ticket.entity;

import com.culturespot.culturespotdomain.common.BaseEntity;
import com.culturespot.culturespotdomain.core.performance.entity.Performance;
import com.culturespot.culturespotdomain.core.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Ticket extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    private int rating;

    private String ticketTitle;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Performance performance;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}




