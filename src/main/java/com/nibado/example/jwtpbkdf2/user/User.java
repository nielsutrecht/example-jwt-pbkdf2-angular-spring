package com.nibado.example.jwtpbkdf2.user;

public class User {
    private final String email;
    private final String role;
    private final String name;

    public User(String email, String name, String role) {
        this.email = email;
        this.role = role;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }
}
