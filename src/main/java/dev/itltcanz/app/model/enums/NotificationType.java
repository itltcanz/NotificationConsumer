package dev.itltcanz.app.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
  SMS("sms-events"),
  EMAIL("email-events"),
  PUSH("push-events"),
  TELEGRAM("telegram-events");

  private final String topic;

  @JsonCreator
  public static NotificationType fromString(String topic) {
    return Arrays.stream(values())
        .filter(value -> value.topic.equalsIgnoreCase(topic))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Неправильный тип уведомления: " + topic));
  }
}
