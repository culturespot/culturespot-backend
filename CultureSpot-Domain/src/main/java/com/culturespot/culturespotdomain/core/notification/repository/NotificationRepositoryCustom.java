package com.culturespot.culturespotdomain.core.notification.repository;

import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface NotificationRepositoryCustom {

  List<Notification> seekAllByUserIdAndGreaterThanIdWithPagination(Long userId, Long id,
      Pageable pageRequest);
}
