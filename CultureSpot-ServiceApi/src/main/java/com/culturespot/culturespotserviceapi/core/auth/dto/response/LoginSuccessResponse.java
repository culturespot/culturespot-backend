package com.culturespot.culturespotserviceapi.core.auth.dto.response;

import com.culturespot.culturespotserviceapi.core.auth.dto.LoginUser;

public record LoginSuccessResponse(
        LoginUser user
) {
}
