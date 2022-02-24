package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.PlaceOrderDetail;
import com.codesoom.sejongdeveloper.repository.PlaceOrderDetailRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceOrderDetailController.class)
@DisplayName("PlaceOrderDetailController 클래스")
@AutoConfigureMockMvc
class PlaceOrderDetailControllerTest {

    private static final Long PLACE_ORDER_ID = 1L;
    private static final Long PLACE_ORDER_DETAIL_ID = 1L;
    private static final Long ITEM_ID = 1L;
    private static final String ITEM_NAME = "test";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private PlaceOrderDetailRepository placeOrderDetailRepository;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Nested
    @DisplayName("발주상세_목록조회_요청을_처리하는_핸들러는")
    class a1 {
        @Nested
        @DisplayName("주어진 아이디의 발주에 대한 발주상세를 찾는 경우")
        class a2 {
            PlaceOrderDetail placeOrderDetail;

            @BeforeEach
            void setUp() {
                Item item = Item.builder()
                        .id(ITEM_ID)
                        .name(ITEM_NAME)
                        .build();
                placeOrderDetail = PlaceOrderDetail.builder()
                        .id(PLACE_ORDER_DETAIL_ID)
                        .item(item)
                        .quantity(1_000.0)
                        .build();

                List<PlaceOrderDetail> list = List.of(placeOrderDetail);

                given(placeOrderDetailRepository.findAllByPlaceOrderId(eq(PLACE_ORDER_ID))).willReturn(list);
            }

            @Test
            @DisplayName("발주상세 목록을 리턴한다")
            void a3() throws Exception {
                String json = objectMapper.writeValueAsString(placeOrderDetail);

                mockMvc.perform(get("/place-order-details/place-orders/" + PLACE_ORDER_ID))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("\"name\":\"test\"")));
            }
        }
    }
}