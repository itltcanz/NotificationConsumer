package dev.itltcanz.app.handler.strategy.event.impl;

import dev.itltcanz.app.handler.strategy.event.doc.NotificationRepoStrategy;
import dev.itltcanz.app.model.entity.TelegramNotificationEntity;
import dev.itltcanz.app.model.enums.NotificationType;
import dev.itltcanz.app.repo.TelegramNotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelegramNotificationHandler implements NotificationRepoStrategy<TelegramNotificationEntity> {

  private final TelegramNotificationRepo notificationRepo;

  @Override
  public NotificationType getTopic() {
    return NotificationType.TELEGRAM;
  }

  @Override
  public TelegramNotificationRepo getRepo() {
    return notificationRepo;
  }

  @Override
  public Class<TelegramNotificationEntity> getEntityClass() {
    return TelegramNotificationEntity.class;
  }

}
