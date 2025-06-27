package com.culturespot.culturespotserviceapi.core.notification.controller.spec;

import com.culturespot.culturespotdomain.core.user.entity.User;
import com.culturespot.culturespotserviceapi.core.auth.annotation.Auth;
import com.culturespot.culturespotserviceapi.core.notification.dto.response.NotificationResponse;
import com.culturespot.culturespotserviceapi.core.notification.dto.response.NotificationResponse.NotificationResponseItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "PRIVATE-알림 API")
public interface NotificationControllerSpec {

  @Operation(
      summary = "알림 목록 조회",
      description = "알림 목록 조회와 관련한 API입니다.",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                      implementation = NotificationResponse.class
                  )
              )
          )
      }
  )
  NotificationResponse getNotifications(
      @PositiveOrZero
      @RequestParam(required = false, defaultValue = "1") Long page,
      @Min(1)
      @Max(50)
      @RequestParam(required = false, defaultValue = "10") Long size,
      @PositiveOrZero
      @RequestParam(required = false, defaultValue = "0") Long lastId,
      @io.swagger.v3.oas.annotations.Parameter(hidden = true)
      @Auth User user
  );

  @Operation(
      summary = "알림 읽음 처리",
      description = "알림 읽음 처리를 합니다.",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                      implementation = NotificationResponse.NotificationResponseItem.class
                  )
              )
          )
      }
  )
  NotificationResponse.NotificationResponseItem readNotification(@PathVariable Long notificationId,
      @io.swagger.v3.oas.annotations.Parameter(hidden = true)
      @Auth User user);


  @Operation(
      summary = "알림 전체 읽음 처리",
      description = "알림 전체 읽음 처리를 합니다.",
      responses = {
          @io.swagger.v3.oas.annotations.responses.ApiResponse(
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(
                      implementation = NotificationResponseItem.class
                  )
              )
          )
      }
  )
  List<NotificationResponseItem> readWholeNotifications(
      @io.swagger.v3.oas.annotations.Parameter(hidden = true)
      @Auth User user);
}