SELECT u.ID             AS user_id,
       u.USERNAME       AS username,
       u.PASSWORD       AS password,
       r.ID              AS role_id,
       r.NAME            AS role
FROM users u
         LEFT JOIN USERS_ROLES u_r
                   ON u.ID = u_r.USER_ID
         LEFT JOIN ROLES r ON u_r.ROLE_ID = r.ID;
