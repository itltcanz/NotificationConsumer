package dev.itltcanz.app.handler.strategy.event.impl;

import dev.itltcanz.app.handler.strategy.event.doc.NotificationRepoStrategy;
import dev.itltcanz.app.model.entity.SmsNotificationEntity;
import dev.itltcanz.app.model.enums.NotificationType;
import dev.itltcanz.app.repo.SmsNotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsNotificationHandler implements NotificationRepoStrategy<SmsNotificationEntity> {

  private final SmsNotificationRepo notificationRepo;

  @Override
  public NotificationType getTopic() {
    return NotificationType.SMS;
  }

  @Override
  public SmsNotificationRepo getRepo() {
    return notificationRepo;
  }

  @Override
  public Class<SmsNotificationEntity> getEntityClass() {
    return SmsNotificationEntity.class;
  }

}
