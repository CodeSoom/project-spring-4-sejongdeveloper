package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("PlaceOrderController 클래스")
@Transactional
class PlaceOrderQueryControllerTest {

    private static final String PLACE_ORDER_NAME = "testName";
    private static final LocalDate PLACE_ORDER_DATE = LocalDate.now();
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlaceOrderRepository placeOrderRepository;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        PlaceOrder placeOrder = PlaceOrder.builder()
                .name(PLACE_ORDER_NAME)
                .date(PLACE_ORDER_DATE)
                .build();

        placeOrderRepository.save(placeOrder);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 발주목록_조회를_요청하는_핸들러는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 검색조건을_만족하는_발주목록을_찾은_경우 {
            PlaceOrderSearchCondition nameCondition;
            PlaceOrderSearchCondition dateCondition;

            @BeforeEach
            void setUp() {
                Pageable pageable = PageRequest.of(0, 10);

                nameCondition = PlaceOrderSearchCondition.builder()
                        .name(PLACE_ORDER_NAME)
                        .pageable(pageable)
                        .build();

                dateCondition = PlaceOrderSearchCondition.builder()
                        .date(PLACE_ORDER_DATE)
                        .pageable(pageable)
                        .build();
            }

            @Test
            @DisplayName("발주목록을 리턴한다")
            void 발주목록을_리턴한다() throws Exception {
                String json = objectMapper.writeValueAsString(nameCondition);

                mockMvc.perform(get("/place-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(PLACE_ORDER_NAME)));

                json = objectMapper.writeValueAsString(dateCondition);

                mockMvc.perform(get("/place-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(PLACE_ORDER_DATE.toString())));
            }
        }
    }
}
