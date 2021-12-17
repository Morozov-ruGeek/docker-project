package com.epam.amorozov.studycenter.repositories;

import com.epam.amorozov.studycenter.models.entities.User;

import java.util.List;

public interface UserRepository {
    User findByUserName(String name);
    List<User> getAllUsers();
}
