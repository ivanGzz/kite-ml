# --- !Ups

CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,
    url TEXT,
    verb TEXT,
    payload TEXT,
    created TIMESTAMP
);

CREATE TABLE network (
    id SERIAL PRIMARY KEY,
    name TEXT,
    path TEXT,
    version INTEGER,
    created TIMESTAMP
);

CREATE TABLE sentence (
    id SERIAL PRIMARY KEY,
    lang TEXT,
    content TEXT,
    sentiment TEXT,
    question BOOLEAN,
    common_ground BOOLEAN,
    created TIMESTAMP
);

CREATE TABLE user_competency (
    id SERIAL PRIMARY KEY,
    project_id INTEGER,
    user_id INTEGER,
    competencies TEXT,
    score TEXT,
    created TIMESTAMP
);

# --- !Downs

DROP TABLE user_competency;
DROP TABLE sentence;
DROP TABLE network;
DROP TABLE audit_log;