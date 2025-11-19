package dev.itltcanz.app.handler;

import dev.itltcanz.app.model.event.ExceptionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerExceptionHandler {

  @EventListener
  public void handleExceptionEvent(ExceptionEvent event) {
    log.error("Ошибка обработки уведомления из топика {}", event.getTopic(), event.getException());
  }
}
