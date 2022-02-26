package com.codesoom.sejongdeveloper.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtUtil {
    private final Key key;

    public JwtUtil(@Value("${jwt.key}") String key) {
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public String encode(String name, Object value) {
        return Jwts.builder()
                .claim(name, value)
                .signWith(key)
                .compact();
    }
}
