package dev.itltcanz.app.model.entity;

import dev.itltcanz.app.model.enums.NotificationType;
import dev.itltcanz.app.util.NotificationTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@Accessors(chain = true)
public abstract class BaseNotificationEntity {

  @Id
  @Column(name = "id")
  @Setter(AccessLevel.NONE)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "topic", nullable = false)
  @Convert(converter = NotificationTypeConverter.class)
  private NotificationType topic;

  @Column(name = "key", nullable = false)
  private UUID key;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "value", nullable = false, columnDefinition = "jsonb")
  private Map<String, Object> value;

  @Column(name = "processed", nullable = false)
  private Boolean processed = false;

  @Setter(AccessLevel.NONE)
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt = Instant.now();
}