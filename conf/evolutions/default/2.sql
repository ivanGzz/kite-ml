# --- !Ups

CREATE TABLE project (
    id INTEGER PRIMARY KEY,
    competencies TEXT,
    CREATED TIMESTAMP
);

ALTER TABLE network ADD COLUMN result TEXT;
ALTER TABLE network ADD COLUMN filepath TEXT;

# --- !Downs

ALTER TABLE network DROP COLUMN filepath;
ALTER TABLE network DROP COLUMN result;

DROP TABLE project;