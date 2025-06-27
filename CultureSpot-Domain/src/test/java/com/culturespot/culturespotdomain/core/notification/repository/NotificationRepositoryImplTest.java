package com.culturespot.culturespotdomain.core.notification.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.culturespot.culturespotdomain.core.notification.entity.Notification;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@ExtendWith(RandomBeansExtension.class)
class NotificationRepositoryImplTest {

  @Autowired
  private NotificationRepository notificationRepository;

  @Test
  void test(
      @Random Notification firstNotification,
      @Random Notification secondNotification) {

    notificationRepository.save(firstNotification);
    notificationRepository.save(secondNotification);
  }

  @Test
  void testSeekAllByUserIdAndGreaterThanIdWithPagination(
      @Random Notification firstNotification,
      @Random Notification secondNotification) {
    // Given: Save test set
    Long userId = 999L;
    notificationRepository.save(firstNotification.toBuilder().userId(userId).build());
    notificationRepository.save(secondNotification.toBuilder().userId(userId).build());

    // When: Querying with pagination
    Pageable pageable = PageRequest.of(0, 10);
    List<Notification> results = notificationRepository.seekAllByUserIdAndGreaterThanIdWithPagination(
        userId, 1L, pageable);

    // Then: Verify the results
    assertNotNull(results);
    assertEquals(1, results.size());
    assertEquals(2, results.get(0).getId());
  }
}