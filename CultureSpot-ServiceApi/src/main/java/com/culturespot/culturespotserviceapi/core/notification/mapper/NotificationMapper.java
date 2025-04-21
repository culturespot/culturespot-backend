package com.culturespot.culturespotserviceapi.core.notification.mapper;

import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import com.culturespot.culturespotserviceapi.core.notification.dto.response.NotificationResponse;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

  NotificationResponse toNotifincationResponse(Notification notification);

  List<NotificationResponse> toNotifincationResponses(List<Notification> notifications);
}
