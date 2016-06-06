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
    private List<String> roles;

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public static UserEntity adminUser(String email) {
        UserEntity entity = new UserEntity();
        entity.setEmail(email);
        entity.setId(UUID.randomUUID());
        entity.setRoles(Arrays.asList("user", "admin"));

        return entity;
    }
}
