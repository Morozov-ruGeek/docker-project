drop table if exists users;
create table users
(
    id       serial primary key,
    username varchar(30) not null unique,
    password varchar(80) not null
);

drop table if exists roles;
create table roles
(
    id   serial primary key,
    name varchar(50) not null unique
);

drop table if exists users_roles;
create table users_roles
(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key (user_id, role_id)
);

drop table if exists students;
create table students
(
    id         serial primary key,
    first_name varchar(128) not null,
    last_name  varchar(128) not null,
    start_date date
);

drop table if exists courses;
create table courses
(
    id     serial primary key,
    name   varchar(128) not null unique,
    amount decimal default 0.0
);

drop table if exists topics;
create table topics
(
    id              serial primary key,
    name            varchar(128) not null unique,
    hours_durations int          not null
);

drop table if exists scores;
create table scores
(
    id         serial not null primary key,
    topic_id   int references topics (id),
    student_id bigint references students (id) on delete cascade,
    score      int
);

drop table if exists students_courses;
create table students_courses
(
    student_id bigint not null references students (id) on delete cascade,
    course_id  bigint references courses (id)
);

drop table if exists course_topics;
create table course_topics
(
    course_id bigint not null references courses (id) on delete cascade,
    topic_id  bigint not null references topics (id),
    primary key (course_id, topic_id)
);

drop table if exists payments;
create table payments
(
    id         serial primary key,
    student_id bigint not null references students (id),
    course_id  bigint not null references courses (id),
    is_paid    boolean default false
);