package com.epam.amorozov.studycenter.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String username;
    private String password;
    private Collection<Role> roles;

    public void addRole(Role role) {
        roles.add(role);
    }
}