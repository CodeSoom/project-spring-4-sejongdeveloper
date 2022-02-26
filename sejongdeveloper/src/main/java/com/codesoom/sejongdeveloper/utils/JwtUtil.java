package com.codesoom.sejongdeveloper.utils;

import com.codesoom.sejongdeveloper.errors.JwtInvalidException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtUtil {
    private final Key tokenKey;

    public JwtUtil(@Value("${jwt.key}") String key) {
        this.tokenKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public String encode(String key, Object value) {
        if (key == null || value == null) {
            throw new JwtInvalidException(key, value);
        }

        return Jwts.builder()
                .claim(key, value)
                .signWith(tokenKey)
                .compact();
    }

}