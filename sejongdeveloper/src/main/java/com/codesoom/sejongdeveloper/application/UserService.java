package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.User;
import com.codesoom.sejongdeveloper.errors.UserNotFoundException;
import com.codesoom.sejongdeveloper.repository.UserRepository;
import com.codesoom.sejongdeveloper.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public String login(String loginId, String password) {
        User user = userRepository.findByLoginIdAndPassword(loginId, password)
                .orElseThrow(() -> new UserNotFoundException(loginId));

        return jwtUtil.encode("userId", user.getId());
    }

    public User findUser(String token) {
        Claims claims = jwtUtil.decode(token);
        Long id = claims.get("userId", Long.class);

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
