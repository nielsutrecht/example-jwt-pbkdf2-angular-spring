package com.nibado.example.jwtpbkdf2.security;

import com.nibado.example.jwtpbkdf2.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class JwtUtil {
    private final String secretKey;
    private final Duration validFor;

    public JwtUtil(String secretKey, Duration validFor) {
        this.secretKey = secretKey;
        this.validFor = validFor;
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String createToken(User user) {
        Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        Date expires = Date.from(LocalDateTime.now().plus(validFor).toInstant(ZoneOffset.UTC));
        return Jwts.builder().setSubject(user.getEmail())
                .claim("user", user)
                .setExpiration(expires)
                .setIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }
}
