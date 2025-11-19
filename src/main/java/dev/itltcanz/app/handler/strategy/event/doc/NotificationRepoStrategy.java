package dev.itltcanz.app.handler.strategy.event.doc;

import dev.itltcanz.app.model.entity.BaseNotificationEntity;
import dev.itltcanz.app.model.enums.NotificationType;
import dev.itltcanz.app.repo.BaseNotificationRepo;

public interface NotificationRepoStrategy<T extends BaseNotificationEntity> {

  NotificationType getTopic();

  BaseNotificationRepo<T> getRepo();

  Class<T> getEntityClass();
}