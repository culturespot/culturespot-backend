package com.culturespot.culturespotserviceapi.common.dto;

import org.springframework.web.bind.annotation.RequestParam;

public record Paging(
        int page,
        int size,
        int lastId
) {
}
