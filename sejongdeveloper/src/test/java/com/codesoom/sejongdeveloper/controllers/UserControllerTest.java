package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.UserService;
import com.codesoom.sejongdeveloper.dto.LoginData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("UserController 클래스")
class UserControllerTest {

    private static final String LOGIN_ID = "test";
    private static final String PASSWORD = "1234";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        given(userService.login(LOGIN_ID, PASSWORD)).willReturn(TOKEN);
    }

    @Nested
    @DisplayName("로그인 요청하는 핸들러는")
    class a {
        @Nested
        @DisplayName("주어진 아이디와 비밀번호가 있는 경우")
        class a1 {
            String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                LoginData loginData = LoginData.builder()
                        .loginId(LOGIN_ID)
                        .password(PASSWORD)
                        .build();

                json = objectMapper.writeValueAsString(loginData);
            }

            @Test
            @DisplayName("토큰을 리턴한다")
            void test() throws Exception {
                mockMvc.perform(post("/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(TOKEN));
            }
        }

        @Nested
        @DisplayName("주어진 아이디와 비밀번호가 없는 경우")
        class a2 {
            String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                LoginData loginData = LoginData.builder().build();

                json = objectMapper.writeValueAsString(loginData);
            }

            @Test
            @DisplayName("Bad Request로 응답한다")
            void test() throws Exception {
                mockMvc.perform(post("/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("로그아웃 요청을 처리하는 핸들러는")
    class b {
        @Nested
        @DisplayName("인증 토큰의 쿠키가 있는 경우")
        class b1 {
            @Test
            @DisplayName("토큰을 만료하고 로그인 페이지로 리다이렉트한다")
            void test() throws Exception {
                mockMvc.perform(get("/users/logout"))
                        .andExpect(status().isOk());
            }
        }
    }
}
