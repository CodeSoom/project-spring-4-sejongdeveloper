package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginData {

    private String loginId; //로그인 아이디

    private String password;    //비밀번호

    @Builder
    public LoginData(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
