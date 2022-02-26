package com.codesoom.sejongdeveloper.interceptors;

import com.codesoom.sejongdeveloper.application.UserService;
import com.codesoom.sejongdeveloper.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<Cookie> foundCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("Authentication"))
                .findFirst();

        if (foundCookie.isEmpty()) {
            response.sendRedirect("/");
            return false;
        }

        try {
            String authentication = foundCookie.get().getValue();
            String token = authentication.substring("Bearer ".length());
            userService.findUser(token);
        } catch (Exception e) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
