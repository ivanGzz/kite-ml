# --- !Ups

CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    payload TEXT NOT NULL,
    created TIMESTAMP
);

# --- !Downs

DROP TABLE audit_log;