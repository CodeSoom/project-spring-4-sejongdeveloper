package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.UserService;
import com.codesoom.sejongdeveloper.dto.LoginData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 유저의 요청에 대하여 관리한다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 주어진 로그인 정보의 유저가 있는 경우 토큰을 응답한다.
     *
     * @param loginData 주어진 로그인 정보
     * @return 주어진 로그인 정보의 유저 토큰
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginData loginData) {
        return userService.login(loginData.getLoginId(), loginData.getPassword());
    }

    /**
     * 로그아웃 요청이 오면 쿠키를 만료한다.
     *
     * @param response 쿠키 만료 응답
     */
    @GetMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("Authentication", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
