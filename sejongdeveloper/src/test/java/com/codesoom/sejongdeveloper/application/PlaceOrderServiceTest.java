package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import com.codesoom.sejongdeveloper.dto.PlaceOrderUpdateRequest;
import com.codesoom.sejongdeveloper.errors.PlaceOrderNotFoundException;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("PlaceOrderService 클래스")
@Transactional
class PlaceOrderServiceTest {

    private static final String PLACE_ORDER_NAME = "발주명";
    private static final Long VALID_PLACE_ORDER_ID = 1L;
    private static final Long INVALID_PLACE_ORDER_ID = 2L;

    private PlaceOrderService placeOrderService;
    private PlaceOrderRepository placeOrderRepository = mock(PlaceOrderRepository.class);
    private PlaceOrderDetailService placeOrderDetailService = mock(PlaceOrderDetailService.class);

    @BeforeEach
    void setUp() {
        placeOrderService = new PlaceOrderService(placeOrderRepository, placeOrderDetailService);

        PlaceOrder placeOrder = PlaceOrder.builder()
                .id(VALID_PLACE_ORDER_ID)
                .name(PLACE_ORDER_NAME)
                .build();

        given(placeOrderRepository.save(any(PlaceOrder.class))).willReturn(placeOrder);

        given(placeOrderRepository.findById(VALID_PLACE_ORDER_ID)).willReturn(Optional.of(placeOrder));
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
                request = PlaceOrderSaveRequest.builder()
                        .name(PLACE_ORDER_NAME)
                        .build();
            }

            @Test
            @DisplayName("발주를 저장한다")
            void 발주를_저장한다() {
                Long savedId = placeOrderService.savePlaceOrder(request);

                assertThat(savedId).isEqualTo(VALID_PLACE_ORDER_ID);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class updatePlaceOrder_메소드는 {
        private static final String UPDATE_PLACE_ORDER_NAME = "수정된 발주명";
        private PlaceOrderUpdateRequest request;

        @BeforeEach
        void setUp() {
            request = PlaceOrderUpdateRequest.builder()
                    .name(UPDATE_PLACE_ORDER_NAME)
                    .build();
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_발주를_찾은_경우 {
            @Test
            @DisplayName("발주를 수정한다")
            void 발주를_수정한다() {
                placeOrderService.updatePlaceOrder(VALID_PLACE_ORDER_ID, request);

                PlaceOrder placeOrder = placeOrderRepository.findById(VALID_PLACE_ORDER_ID).get();
                assertThat(placeOrder.getName()).isEqualTo(UPDATE_PLACE_ORDER_NAME);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_발주를_찾지_못한_경우 {
            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> placeOrderService.updatePlaceOrder(INVALID_PLACE_ORDER_ID, request))
                        .isInstanceOf(PlaceOrderNotFoundException.class);
            }
        }
    }
}
