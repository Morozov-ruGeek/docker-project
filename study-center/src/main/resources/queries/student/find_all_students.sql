SELECT st.ID             AS student_id,
       st.FIRST_NAME     AS first_name,
       st.LAST_NAME      AS last_name,
       st.START_DATE     AS start_date,
       c.ID              AS course_id,
       c.NAME            AS course_name,
       c.AMOUNT          AS course_amount,
       t.ID              AS topic_id,
       t.NAME            AS topic_name,
       t.HOURS_DURATIONS AS topic_hours_durations,
       sc.ID             AS score_id,
       sc.SCORE          AS topic_score
FROM students st
         LEFT JOIN STUDENTS_COURSES s_c
                   ON st.ID = s_c.STUDENT_ID
         LEFT JOIN COURSES c ON s_c.COURSE_ID = c.ID
         LEFT JOIN COURSE_TOPICS ct ON C.ID = ct.COURSE_ID
         LEFT JOIN TOPICS t ON ct.TOPIC_ID = t.ID
         LEFT JOIN SCORES sc ON t.ID = sc.TOPIC_ID and st.ID = sc.STUDENT_ID;