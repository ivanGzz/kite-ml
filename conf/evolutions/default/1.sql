# --- !Ups

CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,
    url TEXT NOT NULL,
    payload TEXT NOT NULL,
    created TIMESTAMP
);

CREATE TABLE assessment (
    id INTEGER PRIMARY KEY,
    score INTEGER,
    team_id INTEGER,
    rubric_id INTEGER,
    rubric_dimension_id INTEGER,
    user_id INTEGER,
    user_type TEXT
);

# --- !Downs

DROP TABLE assessment;
DROP TABLE audit_log;