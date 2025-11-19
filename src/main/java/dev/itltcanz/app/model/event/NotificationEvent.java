package dev.itltcanz.app.model.event;

import dev.itltcanz.app.model.enums.NotificationType;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@SuppressWarnings("ClassCanBeRecord")
public class NotificationEvent {
  @NotNull
  private final NotificationType topic;

  @NotNull
  private final Map<String, Object> value;
}
