package com.culturespot.culturespotserviceapi.common.utils.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import java.util.Map;

public class NamedWrapper<T> extends ApiResponse {

    private final Map<String, T> content;

    public NamedWrapper(String key, T value) {
        this.content = Map.of(key, value);
    }

    @JsonAnyGetter
    public Map<String, T> getContent() {
        return content;
    }
}
