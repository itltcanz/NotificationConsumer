package dev.itltcanz.app.repo;

import dev.itltcanz.app.model.entity.EmailNotificationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotificationRepo extends BaseNotificationRepo<EmailNotificationEntity>,
    JpaRepository<EmailNotificationEntity, UUID> {

  @Query(
      value = """
          SELECT COUNT(*) > 0
          FROM email_inbox
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
