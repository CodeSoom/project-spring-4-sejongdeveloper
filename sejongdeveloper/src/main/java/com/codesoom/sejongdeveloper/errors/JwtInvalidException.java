package com.codesoom.sejongdeveloper.errors;

public class JwtInvalidException extends RuntimeException {
    public JwtInvalidException(String key, Object value) {
        super(String.format("[%s] [%s] JWT에 대한 파라미터가 유효하지 않습니다.\n" +
                "key와 value 값을 확인하세요.", key, value));
    }

    public JwtInvalidException(String token) {
        super(String.format("[%s] JWT에 대한 파라미터가 유효하지 않습니다.\n" +
                "토큰을 확인하세요.", token));
    }
}
