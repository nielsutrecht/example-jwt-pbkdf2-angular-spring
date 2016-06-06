package com.nibado.example.jwtpbkdf2.user;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    UserEntity findByEmail(String email);
}
