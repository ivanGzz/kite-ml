# --- !Ups

CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,
    url TEXT,
    payload TEXT,
    created TIMESTAMP
);

CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    birthday TIMESTAMP,
    active BOOLEAN,
    role_id INTEGER
);

# --- !Downs

DROP TABLE users;
DROP TABLE audit_log;