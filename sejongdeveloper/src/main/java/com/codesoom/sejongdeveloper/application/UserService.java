package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.User;
import com.codesoom.sejongdeveloper.errors.UserNotFoundException;
import com.codesoom.sejongdeveloper.repository.UserRepository;
import com.codesoom.sejongdeveloper.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 유저에 대하여 관리한다.
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * 주어진 로그인 정보의 유저가 있는 경우 토큰을 리턴한다.
     *
     * @param loginId 유저의 로그인 아이디
     * @param password 유저의 비밀번호
     * @return 주어진 로그인 정보의 유저 토큰
     * @throws UserNotFoundException 주어진 로그인 정보의 유저를 찾지 못한 경우
     */
    public String login(String loginId, String password) {
        User user = userRepository.findByLoginIdAndPassword(loginId, password)
                .orElseThrow(() -> new UserNotFoundException(loginId));

        return jwtUtil.encode("userId", user.getId());
    }

    /**
     * 주어진 토큰의 유저가 잇는 경우 유저를 리턴한다.
     *
     * @param token 유저의 토큰
     * @return 주어진 토큰의 유저
     * @throws UserNotFoundException 주어진 토큰의 유저를 찾지 못한 경우
     */
    public User findUser(String token) {
        Claims claims = jwtUtil.decode(token);
        Long id = claims.get("userId", Long.class);

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
