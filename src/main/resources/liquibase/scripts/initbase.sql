-- liquiase formatted sql

-- changeset ilyachernilsev:1
CREATE TABLE IF NOT EXISTS notification_task
(
    key INTEGER PRIMARY KEY,
    id_chat INTEGER,
    message TEXT,
    date_time TIMESTAMP[][]
);