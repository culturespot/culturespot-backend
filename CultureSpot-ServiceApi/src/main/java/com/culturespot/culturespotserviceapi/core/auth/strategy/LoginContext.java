package com.culturespot.culturespotserviceapi.core.auth.strategy;

public record LoginContext(
        String registrationId,
        String email
) {
}

