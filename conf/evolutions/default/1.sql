# --- !Ups

CREATE TABLE audit_log (
    id SERIAL PRIMARY KEY,
    url TEXT,
    payload TEXT,
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

CREATE TABLE class_project (
    id INTEGER PRIMARY KEY,
    due_date TIMESTAMP,
    project_id INTEGER,
    group_class_id INTEGER,
    rubric_id INTEGER
);

CREATE TABLE comment (
    id INTEGER PRIMARY KEY,
    commentable_id INTEGER,
    commentable_type TEXT,
    user_id INTEGER,
    title TEXT,
    body TEXT
);

CREATE TABLE course (
    id INTEGER PRIMARY KEY,
    code TEXT,
    multidisciplinary BOOLEAN,
    grade TEXT,
    school_id INTEGER,
    active BOOLEAN,
    tech BOOLEAN
);

CREATE TABLE group_class (
    id INTEGER PRIMARY KEY,
    group_id INTEGER,
    course_id INTEGER
);

# --- !Downs

DROP TABLE group_class;
DROP TABLE course;
DROP TABLE comment;
DROP TABLE class_project;
DROP TABLE assessment;
DROP TABLE audit_log;