package com.nibado.example.jwtpbkdf2.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nibado.example.jwtpbkdf2.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class JwtUtil {
    private final String secretKey;
    private final Duration validFor;

    public JwtUtil(String secretKey, Duration validFor) {
        this.secretKey = secretKey;
        this.validFor = validFor;
    }

    public User parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();

        return new User(claims.getSubject(), claims.get("name", String.class), claims.get("role", String.class));
    }

    public String createToken(User user) {
        Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        Date expires = Date.from(LocalDateTime.now().plus(validFor).toInstant(ZoneOffset.UTC));
        return Jwts.builder().setSubject(user.getEmail())
                .setExpiration(expires)
                .setId(UUID.randomUUID().toString())    //A nonce. For if we ever want to support blacklisting
                .claim("name", user.getName())
                .claim("role", user.getRole())
                .setIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }
}
