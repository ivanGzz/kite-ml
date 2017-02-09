# --- !Ups

CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,
    url TEXT,
    verb TEXT,
    payload TEXT,
    created TIMESTAMP
);

CREATE TABLE chat_room (
    id INTEGER PRIMARY KEY
);

CREATE TABLE users (
    id INTEGER PRIMARY KEY
);

CREATE TABLE user_competency (
    id SERIAL PRIMARY KEY,
    user_id INTEGER,
    chat_room_id INTEGER,
    competencies INTEGER[]
)

# --- !Downs

DROP TABLE user_competency;
DROP TABLE users;
DROP TABLE chat_room;
DROP TABLE audit_log;