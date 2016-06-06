package com.nibado.example.jwtpbkdf2.user;

import java.util.List;

public class User {
    private final String email;
    private final List<String> roles;

    public User(String email, List<String> roles) {
        this.email = email;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
