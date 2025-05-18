package com.culturespot.culturespotdomain.core.ticket.entity;

import lombok.Getter;

@Getter
public enum TicketSort {
    LATEST_CREATED("최신작성순"),
    LATEST_VISITED("최신방문순"),
    OLDEST_CREATED("과거작성순"),
    OLDEST_VISITED("과거방문순");

    private final String value;

    TicketSort(String value) {
        this.value = value;
    }
}
