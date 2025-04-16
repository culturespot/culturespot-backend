package com.culturespot.culturespotdomain.core.performance.entity;

import lombok.Getter;

@Getter
public enum Event {
    PERFORMANCE_EXHIBITION("공연/전시"),
    EVENT_FESTIVAL("행사/축제"),
    EDU_EXPERIENCE("교육/체험");

    private final String value;

    Event(String name) {
        this.value = name;
    }
}
