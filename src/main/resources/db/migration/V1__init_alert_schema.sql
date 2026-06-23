-- alert_incidents: 같은 fingerprint로 묶인 하나의 문제
CREATE TABLE alert_incidents (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    source        VARCHAR(32)  NOT NULL,                  -- ALERTMANAGER / OCI_COST / OCI_MONITORING
    fingerprint   VARCHAR(255) NOT NULL,
    status        VARCHAR(16)  NOT NULL,                  -- FIRING / RESOLVED / REPEATED
    severity      VARCHAR(16)  NOT NULL,                  -- INFO / WARNING / CRITICAL
    title         VARCHAR(512) NOT NULL,
    description   TEXT         NULL,
    service       VARCHAR(128) NULL,
    resource      VARCHAR(255) NULL,
    labels        JSON         NULL,
    started_at    DATETIME(6)  NOT NULL,
    last_seen_at  DATETIME(6)  NOT NULL,
    resolved_at   DATETIME(6)  NULL,
    notify_count  INT          NOT NULL DEFAULT 0,
    created_at    DATETIME(6)  NOT NULL,
    updated_at    DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_incident_fingerprint (fingerprint),
    KEY idx_incident_status (status),
    KEY idx_incident_source (source)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- alert_event_logs: incident timeline
CREATE TABLE alert_event_logs (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    incident_id  BIGINT       NOT NULL,
    event_type   VARCHAR(32)  NOT NULL,                   -- FIRING / REPEATED / RESOLVED / NOTIFICATION_SENT / NOTIFICATION_FAILED
    message      VARCHAR(1024) NULL,
    value        VARCHAR(255) NULL,
    labels       JSON         NULL,
    raw_payload  TEXT         NULL,
    created_at   DATETIME(6)  NOT NULL,
    PRIMARY KEY (id),
    KEY idx_eventlog_incident (incident_id),
    CONSTRAINT fk_eventlog_incident FOREIGN KEY (incident_id) REFERENCES alert_incidents (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
