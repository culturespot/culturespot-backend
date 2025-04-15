package com.culturespot.culturespotserviceapi.core.user.controller.spec;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.global.swagger.SwaggerErrorResponse;
import com.culturespot.culturespotserviceapi.core.user.dto.request.UserProfileRequest;
import com.culturespot.culturespotserviceapi.core.user.dto.response.UserProfileResponse;
import com.culturespot.culturespotserviceapi.core.user.controller.spec.description.UserControllerDescription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name="사용자 API", description = "사용자와 관련한 API입니다.")
public interface UserControllerSpec {

    @Operation(
            summary = "사용자 정보를 불러오는 API입니다.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = UserControllerDescription.GET_USER_PROFILE_200_RESPONSE,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserProfileResponse.class))
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "801",
                            description = UserControllerDescription.USER_PROFILE_801_RESPONSE,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SwaggerErrorResponse.class)
                            )
                    ),

            }
    )
    UserProfileResponse getUserProfile(@Parameter(hidden = true) User user);


    @Operation(
            summary = "사용자 정보를 수정하는 API입니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = UserControllerDescription.UPDATE_USER_PROFILE_REQUEST
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200"
                    )
            }
    )
    void updateUserProfile(
            @Parameter(hidden = true) User user,
            @Valid @RequestBody UserProfileRequest request
    );
}
