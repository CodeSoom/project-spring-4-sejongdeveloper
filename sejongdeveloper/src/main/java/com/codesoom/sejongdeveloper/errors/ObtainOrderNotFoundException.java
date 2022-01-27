package com.codesoom.sejongdeveloper.errors;

public class ObtainOrderNotFoundException extends RuntimeException {
    public ObtainOrderNotFoundException(Long id) {
        super(String.format("[%d] 수주를 찾을 수 없습니다.\n" +
                            "수주의 일련번호를 확인하세요.", id));
    }
}
