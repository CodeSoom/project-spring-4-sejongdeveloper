package com.codesoom.sejongdeveloper.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ReleaseOrderController 클래스")
@WebMvcTest(ReleaseOrderController.class)
class ReleaseOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class 출고저장_요청을_처리하는_핸들러는 {
        @Nested
        class 유효성_검사를_통과한_경우 {
            @Test
            void 회원을_저장한다() throws Exception {
                mockMvc.perform(post("/release-orders"))
                        .andExpect(status().isCreated());
            }
        }
    }

}