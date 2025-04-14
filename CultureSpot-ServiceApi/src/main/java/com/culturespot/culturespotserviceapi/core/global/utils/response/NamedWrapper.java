package com.culturespot.culturespotserviceapi.core.global.utils.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import java.util.Map;

public class NamedWrapper extends ApiResponse{
    private final Map<String, Object> content;

    public NamedWrapper(String key, Object value) {
        this.content = Map.of(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getContent() {
        return content;
    }
}
