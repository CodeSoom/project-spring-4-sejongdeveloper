package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class LoginData {

    @NotNull
    private String loginId; //로그인 아이디

    @NotNull
    private String password;    //비밀번호

    @Builder
    public LoginData(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
