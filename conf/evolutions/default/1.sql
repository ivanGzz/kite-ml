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

CREATE TABLE product_skill (
    id INTEGER PRIMARY KEY,
    product_id INTEGER,
    skill_id INTEGER,
    value_ INTEGER
);

CREATE TABLE project (
    id INTEGER PRIMARY KEY,
    author_id INTEGER,
    project_type_id INTEGER,
    parent_project_id INTEGER,
    teams_count INTEGER,
    team_member_count INTEGER
);

CREATE TABLE project_type (
    id INTEGER PRIMARY KEY,
    name TEXT,
    description TEXT,
    code TEXT
);

CREATE TABLE role (
    id INTEGER PRIMARY KEY,
    name TEXT
);

CREATE TABLE rubric (
    id INTEGER PRIMARY KEY,
    code TEXT,
    author_id INTEGER
);

CREATE TABLE rubric_dimension (
    id INTEGER PRIMARY KEY,
    code TEXT,
    min_score INTEGER,
    medium_score INTEGER,
    max_score INTEGER,
    rubric_id INTEGER,
    author_id INTEGER
);

CREATE TABLE school (
    id INTEGER PRIMARY KEY,
    town TEXT,
    state TEXT,
    country TEXT,
    active BOOLEAN
);

CREATE TABLE skill (
    id INTEGER PRIMARY KEY,
    name TEXT,
    code TEXT
);

CREATE TABLE subject (
    id INTEGER PRIMARY KEY,
    course_id INTEGER
);

CREATE TABLE sub_topic (
    id INTEGER PRIMARY KEY,
    code TEXT,
    topic_id INTEGER
);

CREATE TABLE team (
    id INTEGER PRIMARY KEY,
    published BOOLEAN,
    class_project_id INTEGER,
    project_id INTEGER,
    avg_score REAL,
    views_count INTEGER,
    likes_count INTEGER
);

CREATE TABLE team_member (
    id INTEGER PRIMARY KEY,
    team_id INTEGER,
    user_id INTEGER,
    project_id INTEGER,
    role TEXT
);

CREATE TABLE topic (
    id INTEGER PRIMARY KEY,
    code TEXT,
    subject_id INTEGER
);

CREATE TABLE users (
    id INTEGER PRIMARY KEY,
    birthday TIMESTAMP,
    active BOOLEAN,
    role_id INTEGER
);

# --- !Downs

DROP TABLE users;
DROP TABLE topic;
DROP TABLE team_member;
DROP TABLE team;
DROP TABLE sub_topic;
DROP TABLE subject;
DROP TABLE skill;
DROP TABLE school;
DROP TABLE rubric_dimension;
DROP TABLE rubric;
DROP TABLE role;
DROP TABLE project_type;
DROP TABLE project;
DROP TABLE product_skill;
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