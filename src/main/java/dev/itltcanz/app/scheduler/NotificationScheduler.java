package dev.itltcanz.app.scheduler;

import dev.itltcanz.app.service.impl.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

  private final NotificationService notificationService;

  @Scheduled(fixedDelayString = "${inbox.delay-ms}")
  private void handleNotifications() {
    notificationService.handleNotifications();
  }
}
