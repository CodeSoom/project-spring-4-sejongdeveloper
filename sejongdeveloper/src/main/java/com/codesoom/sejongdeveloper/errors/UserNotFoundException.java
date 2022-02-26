package com.codesoom.sejongdeveloper.errors;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String loginId) {
        super(String.format("[%d] 유저를 찾을 수 없습니다.\n" +
                "유저의 아이디 또는 비밀번호를 확인하세요.", loginId));
    }
}
