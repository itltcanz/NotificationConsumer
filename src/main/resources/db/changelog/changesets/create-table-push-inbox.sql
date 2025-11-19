--liquibase formatted sql
--changeset Perevertaylo Ilya:2025-09-17-create-table-push-inbox

CREATE TABLE IF NOT EXISTS push_inbox (
    id          UUID            NOT NULL    DEFAULT gen_random_uuid()   PRIMARY KEY,
    created_at  TIMESTAMPTZ     NOT NULL    DEFAULT now(),
    topic       VARCHAR(255)    NOT NULL,
    key         UUID            NOT NULL,
    value       JSONB           NOT NULL,
    processed   BOOLEAN         NOT NULL    DEFAULT FALSE
)