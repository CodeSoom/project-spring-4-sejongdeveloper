package com.codesoom.sejongdeveloper.interceptors;

import com.codesoom.sejongdeveloper.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<String> authentication = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("Authentication"))
                .map(Cookie::getValue)
                .findAny();

        if (authentication.isEmpty()) {
            response.sendRedirect("/");
            return false;
        }

        try {
            String token = authentication.get();
            userService.findUser(token);
        } catch (Exception e) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
