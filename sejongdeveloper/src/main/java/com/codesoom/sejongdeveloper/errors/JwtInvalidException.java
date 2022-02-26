package com.codesoom.sejongdeveloper.errors;

public class JwtInvalidException extends RuntimeException {
    public JwtInvalidException(String key, Object value) {
        super(String.format("[%s] [%s] JWT에 대한 파라미터가 유효하지 않습니다.", key, value));
    }
}
