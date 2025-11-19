package dev.itltcanz.app.repo;

import dev.itltcanz.app.model.entity.SmsNotificationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsNotificationRepo extends BaseNotificationRepo<SmsNotificationEntity>,
    JpaRepository<SmsNotificationEntity, UUID> {
  @Query(
      value = """
          SELECT COUNT(*) > 0
          FROM sms_inbox
          WHERE key = :key
            AND value = CAST(:valueJson AS jsonb)
          """,
      nativeQuery = true
  )
  boolean existsByKeyAndValue(
      @Param("key") UUID key,
      @Param("valueJson") String valueJson
  );
}
