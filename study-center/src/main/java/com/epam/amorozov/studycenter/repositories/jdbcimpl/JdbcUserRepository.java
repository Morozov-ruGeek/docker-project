package com.epam.amorozov.studycenter.repositories.jdbcimpl;

import com.epam.amorozov.studycenter.models.entities.Student;
import com.epam.amorozov.studycenter.models.entities.User;
import com.epam.amorozov.studycenter.repositories.UserRepository;
import com.epam.amorozov.studycenter.utils.ResourceReader;
import com.epam.amorozov.studycenter.utils.extractors.UserExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final String GET_ALL_USERS_SQL_QUERY_PATH = "classpath:queries/user/find_all_users.sql";
    private static final String GET_USER_BY_USERNAME_SQL_QUERY_PATH = "classpath:queries/user/find_user_by_username.sql";

    private final JdbcTemplate jdbcTemplate;
    private final ResourceReader resourceReader;
    private final UserExtractor userExtractor;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, ResourceReader resourceReader, UserExtractor userExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceReader = resourceReader;
        this.userExtractor = userExtractor;
    }

    @Override
    public User findByUserName(String name) {
        String getStudentByIdSql = resourceReader.readFileToString(GET_USER_BY_USERNAME_SQL_QUERY_PATH);
        List<User> users = jdbcTemplate.query(getStudentByIdSql, userExtractor, name);
        if (users.isEmpty()) {
            new Student();
        }
        return users.get(0);
    }

    @Override
    public List<User> getAllUsers() {
        String getAllStudentsSql = resourceReader.readFileToString(GET_ALL_USERS_SQL_QUERY_PATH);
        return jdbcTemplate.query(getAllStudentsSql, userExtractor);
    }
}
