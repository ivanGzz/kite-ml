# --- !Ups

CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,
    url TEXT,
    verb TEXT,
    payload TEXT,
    created TIMESTAMP
);

CREATE TABLE user_competency (
    id SERIAL PRIMARY KEY,
    project_id INTEGER,
    user_id INTEGER,
    competencies TEXT
)

# --- !Downs

DROP TABLE user_competency;
DROP TABLE users;
DROP TABLE chat_room;
DROP TABLE audit_log;