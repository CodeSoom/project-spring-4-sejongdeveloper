package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.User;
import com.codesoom.sejongdeveloper.errors.UserNotFoundException;
import com.codesoom.sejongdeveloper.repository.UserRepository;
import com.codesoom.sejongdeveloper.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("UserService 클래스")
class UserServiceTest {

    private static final Long USER_ID = 1L;
    private static final String LOGIN_ID = "test";
    private static final String VALID_PASSWORD = "1234";
    private static final String INVALID_PASSWORD = VALID_PASSWORD + "1234";

    private static final String KEY = "12345678901234567890123456789012";
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";

    private UserService userService;
    private UserRepository userRepository;
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        jwtUtil = new JwtUtil(KEY);
        userService = new UserService(userRepository, jwtUtil);

        User user = User.builder()
                .id(USER_ID)
                .loginId(LOGIN_ID)
                .password(VALID_PASSWORD)
                .build();

        given(userRepository.findByLoginIdAndPassword(LOGIN_ID, VALID_PASSWORD)).willReturn(Optional.of(user));
    }

    @Nested
    @DisplayName("login 메소드는")
    class a {
        @Nested
        @DisplayName("주어진 아이디와 비밀번호의 유저가 있는 경우")
        class a1 {
            @Test
            @DisplayName("토큰을 리턴한다")
            void test() {
                String token = userService.login(LOGIN_ID, VALID_PASSWORD);
                assertThat(token).isEqualTo(VALID_TOKEN);
            }
        }

        @Nested
        @DisplayName("주어진 아이디와 비밀번호의 유저가 없는 경우")
        class a2 {
            @Test
            @DisplayName("예외를 리턴한다")
            void test() {
                assertThatThrownBy(() -> userService.login(LOGIN_ID, INVALID_PASSWORD))
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("findUser 메소드는")
    class b {
        @Nested
        @DisplayName("주어진 토큰의 유저가 있는 경우")
        class b1 {
            @Test
            @DisplayName("유저를 리턴한다.")
            void test() {
                User user = userService.findUser(VALID_TOKEN);
                assertThat(user).isNotNull();
            }
        }
    }
}
