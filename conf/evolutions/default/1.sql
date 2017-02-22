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
    competencies TEXT,
    score TEXT
);

# --- !Downs

DROP TABLE user_competency;
DROP TABLE audit_log;