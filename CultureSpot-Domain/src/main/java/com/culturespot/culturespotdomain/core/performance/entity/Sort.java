package com.culturespot.culturespotdomain.core.performance.entity;

import lombok.Getter;

@Getter
public enum Sort {
    LATEST("최신"),
    POPULAR("인기"),
    OLDEST("오래된");

    private final String value;

    Sort(String value) {
        this.value = value;
    }
}
