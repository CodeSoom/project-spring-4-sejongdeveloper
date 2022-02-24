package com.codesoom.sejongdeveloper.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceOrderDetailController.class)
@DisplayName("PlaceOrderDetailController 클래스")
class PlaceOrderDetailControllerTest {

    private static final Long PLACE_ORDER_ID = 1L;
    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("발주상세_목록조회_요청을_처리하는_핸들러는")
    class a1 {
        @Nested
        @DisplayName("주어진 아이디의 발주에 대한 발주상세를 찾는 경우")
        class a2 {
            @Test
            @DisplayName("발주상세 목록을 리턴한다")
            void a3() throws Exception {
                mockMvc.perform(get("/place-order-details/place-orders/" + PLACE_ORDER_ID))
                        .andExpect(status().isOk());
            }
        }
    }
}