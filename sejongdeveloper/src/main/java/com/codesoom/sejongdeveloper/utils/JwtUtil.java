package com.codesoom.sejongdeveloper.utils;

import com.codesoom.sejongdeveloper.errors.JwtInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * JWT에 대하여 관리한다.
 */
@Component
public class JwtUtil {
    private final Key tokenKey;

    public JwtUtil(@Value("${jwt.key}") String key) {
        this.tokenKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 주어진 파라미터에 대한 토큰을 응답한다.
     *
     * @param key   토큰 키
     * @param value 토큰 값
     * @return  주어진 key와 value에 대한 토큰
     * @throws JwtInvalidException 주어진 파라미터에 대한 값이 없는 경우
     */
    public String encode(String key, Object value) {
        if (key == null || value == null) {
            throw new JwtInvalidException(key, value);
        }

        return Jwts.builder()
                .claim(key, value)
                .signWith(tokenKey)
                .compact();
    }

    /**
     * 주어진 토큰에 대한 Claims를 리턴한다.
     *
     * @param token JWT
     * @return 주어진 토큰에 대한 Claims
     * @throws JwtInvalidException 토큰에 대한 값이 없는 경우 또는 토큰이 잘못된 경우
     */
    public Claims decode(String token) {
        if (token == null || token.isBlank()) {
            throw new JwtInvalidException(token);
        }

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(tokenKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new JwtInvalidException(token);
        }
    }
}
