SELECT p.ID AS id,
       p.STUDENT_ID AS student_id,
       p.COURSE_ID AS course_id,
       p.IS_PAID AS is_paid
FROM payments AS p
WHERE ID = ?;