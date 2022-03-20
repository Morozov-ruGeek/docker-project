package com.epam.amorozov.studycenter.repositories.jpaimpl;

import com.epam.amorozov.studycenter.models.entities.User;
import com.epam.amorozov.studycenter.repositories.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ConditionalOnBean(name = "sessionFactory")
public class CrudUserRepository implements UserRepository {

    private final SessionFactory factory;

    @Autowired
    public CrudUserRepository(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public User findByUserName(String name) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
