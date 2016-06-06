package com.nibado.example.jwtpbkdf2.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
public class UserEntity {
    @Id
    private UUID id;

    private String email;
    private String password;
    private String role;
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static UserEntity adminUser(String email, String name) {
        UserEntity entity = new UserEntity();
        entity.setEmail(email);
        entity.setName(name);
        entity.setId(UUID.randomUUID());
        entity.setRole("admin");

        return entity;
    }
}
