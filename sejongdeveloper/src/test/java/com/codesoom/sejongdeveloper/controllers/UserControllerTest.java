package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.UserService;
import com.codesoom.sejongdeveloper.domain.User;
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
                User user = User.builder()
                        .loginId(LOGIN_ID)
                        .password(PASSWORD)
                        .build();

                json = objectMapper.writeValueAsString(user);
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
    }
}