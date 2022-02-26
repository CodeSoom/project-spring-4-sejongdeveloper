package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.UserService;
import com.codesoom.sejongdeveloper.domain.User;
import com.codesoom.sejongdeveloper.dto.LoginData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/login")
    public String login(@RequestBody LoginData loginData) {
        return userService.login(loginData.getLoginId(), loginData.getPassword());
    }
}
