package dev.itltcanz.app.repo;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseNotificationRepo<T> {

  boolean existsByKeyAndValue(UUID key, String valueJson);

  Page<T> findByProcessedIsFalse(Pageable pageable);
}
