package dev.itltcanz.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NotificationConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotificationConsumerApplication.class, args);
  }

}
