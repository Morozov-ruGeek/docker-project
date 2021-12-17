insert into STUDENTS(id, first_name, last_name, start_date)
VALUES (1, 'Aleksei', 'Morozov', '2021-10-01'),
       (2, 'Aleksandra', 'Morozova', '2021-11-15'),
       (3, 'Vladislav', 'Morozov', '2021-11-01'),
       (4, 'Vladimir', 'Krasnosolnishko', '2021-10-01'),
       (5, 'Ivan', 'Grozny', '2021-10-15');

insert into COURSES(id, NAME, amount)
values (1, 'materials_science', 200.0),
       (2, 'probability_theory', 300.0);

insert into TOPICS(id, name, hours_durations)
values (1, 'structure', 6),
       (2, 'specifications', 8),
       (3, 'manufacture', 12),
       (4, 'thermodynamics', 14),
       (5, 'kinetics', 6),
       (6, 'basic_concepts', 8),
       (7, 'ways_of_determining', 10),
       (8, 'total_probability_formula_and_Bayes_formula', 4),
       (9, 'poisson_formula', 4);

insert into SCORES(id, topic_id, student_id, score)
values (1, 1, 1, 70),
       (2, 2, 1, 80),
       (3, 3, 1, 90),
       (4, 4, 1, 100),
       (5, 5, 1, 60),
       (6, 6, 4, 30),
       (7, 7, 4, 40),
       (8, 8, 4, 50),
       (9, 9, 4, 30);

insert into students_courses(student_id, course_id)
values (1, 1),
       (2, 1),
       (3, 1),
       (4, 2);

insert into course_topics(course_id, topic_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 6),
       (2, 7),
       (2, 8),
       (2, 9);

insert into roles (name)
values ('ROLE_STUDENT'),
       ('ROLE_TEACHER');

insert into users (username, password)
values ('student', '$2a$12$rGWj2mBSHptwtooswQ.BReuho90.JP9KEIA93bv.4swdmK4hs0rHq'),
       ('teacher', '$2a$12$rGWj2mBSHptwtooswQ.BReuho90.JP9KEIA93bv.4swdmK4hs0rHq');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 2);

insert into payments(id, student_id, course_id)
values (1, 1, 1),
       (2, 4, 2);