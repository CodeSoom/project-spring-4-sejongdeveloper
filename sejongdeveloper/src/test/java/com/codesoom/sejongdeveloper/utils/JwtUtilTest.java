package com.codesoom.sejongdeveloper.utils;

import com.codesoom.sejongdeveloper.errors.JwtInvalidException;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("JwtUtil 클래스")
class JwtUtilTest {
    private static final String TOKEN_KEY = "userId";
    private static final Long TOKEN_VALUE = 1L;
    private static final String KEY = "12345678901234567890123456789012";
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(KEY);
    }

    @Nested
    @DisplayName("encode 메소드는")
    class a {
        @Nested
        @DisplayName("주어진 유저의 아이디를 받은 경우")
        class a1 {
            @Test
            @DisplayName("토큰을 리턴한다")
            void a1_1() {
                String token = jwtUtil.encode(TOKEN_KEY, TOKEN_VALUE);

                assertThat(token).isEqualTo(VALID_TOKEN);
            }
        }

        @Nested
        @DisplayName("파라미터가 null인 경우")
        class a2 {
            @Test
            @DisplayName("예외를 던진다")
            void a2_1() {
                assertThatThrownBy(() -> jwtUtil.encode(TOKEN_KEY, null))
                        .isInstanceOf(JwtInvalidException.class);

                assertThatThrownBy(() -> jwtUtil.encode(null, TOKEN_VALUE))
                        .isInstanceOf(JwtInvalidException.class);

                assertThatThrownBy(() -> jwtUtil.encode(null, null))
                        .isInstanceOf(JwtInvalidException.class);
            }
        }
    }

    @Nested
    @DisplayName("decode 메소드는")
    class b {
        @Nested
        @DisplayName("주어진 토큰이 있는 경우")
        class b1 {
            @Test
            @DisplayName("Claims를 리턴한다")
            void b1_1() {
                Claims claims = jwtUtil.decode(VALID_TOKEN);

                assertThat(claims).isNotNull();
                assertThat(claims.get(TOKEN_KEY, Long.class)).isEqualTo(TOKEN_VALUE);
            }
        }

        @Nested
        @DisplayName("주어진 토큰이 없는 경우")
        class b2 {
            @Test
            @DisplayName("예외를 던진다")
            void b2_1() {
                assertThatThrownBy(() -> jwtUtil.decode(VALID_TOKEN))
                        .isInstanceOf(JwtInvalidException.class);
            }
        }
    }
}
