SELECT c.ID              AS course_id,
       c.NAME            AS course_name,
       c.AMOUNT          AS course_amount,
       t.ID              AS topic_id,
       t.NAME            AS topic_name,
       t.HOURS_DURATIONS AS topic_hours_durations
FROM courses c
         LEFT JOIN COURSE_TOPICS CT ON C.ID = CT.COURSE_ID
         LEFT JOIN TOPICS T ON CT.TOPIC_ID = T.ID