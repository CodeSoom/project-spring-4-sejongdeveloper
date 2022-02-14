package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("PlaceOrderService 클래스")
@Transactional
class PlaceOrderServiceTest {

    private static final String PLACE_ORDER_NAME = "발주명";
    private static final Long PLACE_ORDER_ID = 1L;

    private PlaceOrderService placeOrderService;

    @BeforeEach
    void setUp() {
        placeOrderService = new PlaceOrderService();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class savePlaceOrder_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유효한_파라미터인_경우 {
            private PlaceOrderSaveRequest request;

            @BeforeEach
            void setUp() {
                request = new PlaceOrderSaveRequest();
                request.setName(PLACE_ORDER_NAME);
            }

            @Test
            @DisplayName("발주를 저장한다")
            void 발주를_저장한다() {
                Long savedId = placeOrderService.savePlaceOrder(request);

                assertThat(savedId).isEqualTo(PLACE_ORDER_ID);
            }
        }
    }

}