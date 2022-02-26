package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.UserService;
import com.codesoom.sejongdeveloper.dto.LoginData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginData loginData) {
        return userService.login(loginData.getLoginId(), loginData.getPassword());
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("Authentication", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/");
    }
}
