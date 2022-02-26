package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DisplayName("UserService 클래스")
class UserServiceTest {

    private static final String LOGIN_ID = "test";
    private static final String PASSWORD = "1234";

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
    }

    @Nested
    @DisplayName("login 메소드는")
    class a {
        @Nested
        @DisplayName("주어진 아이디와 비밀번호의 유저가 있는 경우")
        class a1 {
            @Test
            @DisplayName("유저를 리턴한다")
            void test() {
                User user = userService.login(LOGIN_ID, PASSWORD);
                assertThat(user).isNotNull();
            }
        }
    }
}