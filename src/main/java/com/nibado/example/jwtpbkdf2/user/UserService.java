package com.nibado.example.jwtpbkdf2.user;

import com.nibado.example.jwtpbkdf2.security.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashUtil hashUtil;

    @PostConstruct
    public void init() {
        UserEntity userEntity = userRepository.findByEmail("admin@example.com");
        if(userEntity == null) {
            userEntity = UserEntity.adminUser("admin@example.com");
            userEntity.setPassword(hashUtil.createHash("admin"));
            userRepository.save(userEntity);
        }
    }
}
