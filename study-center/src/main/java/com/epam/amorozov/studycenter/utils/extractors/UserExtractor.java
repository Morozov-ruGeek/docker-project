package com.epam.amorozov.studycenter.utils.extractors;

import com.epam.amorozov.studycenter.models.entities.Role;
import com.epam.amorozov.studycenter.models.entities.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserExtractor implements ResultSetExtractor<List<User>> {
    private static final String USERNAME_COLUMN = "username";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String USER_PASSWORD_COLUMN = "password";
    private static final String ROLE_COLUMN = "role";
    private static final String ROLE_ID_COLUMN = "role_id";

    @Override
    public List<User> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<String, User> userMap = new HashMap<>();
        Map<String, Role>roleMap = new HashMap();
        while (resultSet.next()){
            String username = resultSet.getString(USERNAME_COLUMN);
            User user = userMap.get(username);
            if(user == null){
                user = new User();
                user.setId(resultSet.getLong(USER_ID_COLUMN));
                user.setUsername(resultSet.getString(USERNAME_COLUMN));
                user.setPassword(resultSet.getString(USER_PASSWORD_COLUMN));
                user.setRoles(new ArrayList<>());
            }

            String roleName = resultSet.getString(ROLE_COLUMN);
            Role role = roleMap.get(roleName);
            if(role == null){
                role = new Role();
                role.setId(resultSet.getLong(ROLE_ID_COLUMN));
                role.setName(resultSet.getString(ROLE_COLUMN));
                roleMap.put(roleName, role);
                user.addRole(role);
            }

            userMap.put(username, user);
        }
        return new ArrayList<>(userMap.values());
    }
}
