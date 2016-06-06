package com.nibado.example.jwtpbkdf2.security;

import com.nibado.example.jwtpbkdf2.user.User;
import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by nielsdommerholt on 06/06/16.
 */
public class JwtUtilTest {
    private JwtUtil jwtUtil;

    @Before
    public void setup() {
        jwtUtil = new JwtUtil("SecretKey", Duration.ofMillis(500));
    }

    @Test
    public void testHappyFlow() {
        User admin = new User("admin@example.com", Arrays.asList("user", "admin"));
        User user = new User("user@example.com", Arrays.asList("user"));
        String adminToken = jwtUtil.createToken(admin);
        String userToken = jwtUtil.createToken(user);

        Claims adminClaims = jwtUtil.parseToken(adminToken);

        assertThat(adminClaims.getSubject()).isEqualTo(admin.getEmail());
        assertThat(adminClaims.get("user", User.class).getEmail()).isEqualTo(admin.getEmail());
    }
}