# --- !Ups

CREATE TABLE project (
    id INTEGER PRIMARY KEY,
    competencies TEXT,
    CREATED TIMESTAMP
);

CREATE TABLE npl (
    id SERIAL PRIMARY KEY,
    name TEXT,
    path TEXT,
    filepath TEXT,
    version INTEGER,
    created TIMESTAMP
)

ALTER TABLE network ADD COLUMN result TEXT;
ALTER TABLE network ADD COLUMN filepath TEXT;
ALTER TABLE network ADD COLUMN project INTEGER;

# --- !Downs

ALTER TABLE network DROP COLUMN project;
ALTER TABLE network DROP COLUMN filepath;
ALTER TABLE network DROP COLUMN result;

DROP TABLE project;