package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("PlaceOrderService 클래스")
@Transactional
class PlaceOrderServiceTest {

    private static final String PLACE_ORDER_NAME = "발주명";
    private static final Long PLACE_ORDER_ID = 1L;

    private PlaceOrderService placeOrderService;
    private PlaceOrderRepository placeOrderRepository;

    @BeforeEach
    void setUp() {
        placeOrderRepository = mock(PlaceOrderRepository.class);

        placeOrderService = new PlaceOrderService(placeOrderRepository);

        PlaceOrder placeOrder = PlaceOrder.builder()
                .id(PLACE_ORDER_ID)
                .name(PLACE_ORDER_NAME)
                .build();

        given(placeOrderRepository.save(any(PlaceOrder.class))).willReturn(placeOrder);
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
                PlaceOrder placeOrder = placeOrderService.savePlaceOrder(request);

                assertThat(placeOrder.getId()).isEqualTo(PLACE_ORDER_ID);
            }
        }
    }

}