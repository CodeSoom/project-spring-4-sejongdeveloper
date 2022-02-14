package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceOrderController.class)
@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("PlaceOrderController 클래스")
class PlaceOrderControllerTest {

    private static final String PLACE_ORDER_NAME = "발주명";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 출고등록_요청을_처리하는_핸들러는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_파라미터가_유효성_검사를_통과한_경우 {
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                PlaceOrderSaveRequest request = new PlaceOrderSaveRequest();
                request.setName(PLACE_ORDER_NAME);

                json = objectMapper.writeValueAsString(request);
            }

            @Test
            @DisplayName("발주를_저장한다")
            void 발주를_저장한다() throws Exception {
                mockMvc.perform(post("/place-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isOk());
            }
        }
    }
}