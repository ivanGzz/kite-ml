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

CREATE TABLE group_class_teacher (
    id INTEGER PRIMARY KEY,
    group_class_id INTEGER,
    teacher_id INTEGER
);

CREATE TABLE group_student (
    id INTEGER PRIMARY KEY,
    group_id INTEGER,
    student_id INTEGER
);

CREATE TABLE likes (
    id INTEGER PRIMARY KEY,
    likeable_id INTEGER,
    likeable_type TEXT,
    user_id INTEGER
);

CREATE TABLE post (
    id INTEGER PRIMARY KEY,
    user_id INTEGER,
    team_id INTEGER,
    product_id INTEGER,
    post_type TEXT,
    comment TEXT
);

CREATE TABLE product (
    id INTEGER PRIMARY KEY,
    product_type TEXT,
    project_id INTEGER,
    product_order INTEGER,
    points INTEGER
);

# --- !Downs

DROP TABLE product;
DROP TABLE post;
DROP TABLE likes;
DROP TABLE group_student;
DROP TABLE group_class_teacher;
DROP TABLE group_class;
DROP TABLE course;
DROP TABLE comment;
DROP TABLE class_project;
DROP TABLE assessment;
DROP TABLE audit_log;