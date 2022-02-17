package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.domain.PlaceOrderDetail;
import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailUpdateRequest;
import com.codesoom.sejongdeveloper.repository.ItemRepository;
import com.codesoom.sejongdeveloper.repository.PlaceOrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"InnerClassMaybeStatic", "NonAsciiCharacters"})
@DisplayName("PlaceOrderDetailService 클래스")
class PlaceOrderDetailServiceTest {

    private static final Long ITEM_ID = 1L;
    private static final Long PLACE_ORDER_DETAIL_ID = 1L;
    private static final Double QUANTITY = 1_000.0;
    private PlaceOrderDetailService placeOrderDetailService;
    private PlaceOrderDetailRepository placeOrderDetailRepository = mock(PlaceOrderDetailRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeEach
    void setUp() {
        placeOrderDetailService = new PlaceOrderDetailService(placeOrderDetailRepository, itemRepository);

        Item item = Item.builder()
                .id(ITEM_ID)
                .quantity(1_000.0)
                .build();

        given(itemRepository.findById(ITEM_ID)).willReturn(Optional.of(item));

        PlaceOrder placeOrder = PlaceOrder.builder().build();

        PlaceOrderDetail placeOrderDetail = PlaceOrderDetail.builder()
                .id(PLACE_ORDER_DETAIL_ID)
                .item(item)
                .quantity(QUANTITY)
                .placeOrder(placeOrder)
                .build();

        given(placeOrderDetailRepository.findById(PLACE_ORDER_DETAIL_ID)).willReturn(Optional.of(placeOrderDetail));
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class savePlaceOrderDetails_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유효한_파라미터인_경우 {
            private PlaceOrder placeOrder;
            private List<PlaceOrderDetailSaveRequest> placeOrderDetails;
            private final Double QUANTITY = 1_000.0;

            @BeforeEach
            void setUp() {
                placeOrder = PlaceOrder.builder().build();

                PlaceOrderDetailSaveRequest request = PlaceOrderDetailSaveRequest.builder()
                        .itemId(ITEM_ID)
                        .quantity(QUANTITY)
                        .build();

                placeOrderDetails = List.of(request);
            }

            @Test
            @DisplayName("발주상세를 저장한다")
            void 발주상세를_저장한다() {
                Double beforeQuantity = itemRepository.findById(ITEM_ID).get().getQuantity();

                placeOrderDetailService.savePlaceOrderDetails(placeOrder, placeOrderDetails);

                Double afterQuantity = itemRepository.findById(ITEM_ID).get().getQuantity();

                assertThat(afterQuantity - beforeQuantity).isEqualTo(QUANTITY);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class updatePlaceOrderDetails_메소드는 {
        private static final double UPDATE_QUANTITY = 1_004.0;
        private PlaceOrder placeOrder;
        private List<PlaceOrderDetailUpdateRequest> placeOrderDetails;

        @BeforeEach
        void setUp() {
            placeOrder = PlaceOrder.builder().build();

            PlaceOrderDetailUpdateRequest request = PlaceOrderDetailUpdateRequest.builder()
                    .id(PLACE_ORDER_DETAIL_ID)
                    .itemId(ITEM_ID)
                    .quantity(UPDATE_QUANTITY)
                    .build();

            placeOrderDetails = List.of(request);
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_발주상세를_찾은_경우 {
            @Test
            @DisplayName("발주상세를 수정한다")
            void 발주상세를_수정한다() {
                PlaceOrderDetail result = placeOrderDetailRepository.findById(PLACE_ORDER_DETAIL_ID).get();
                Double beforeQuantity = result.getItem().getQuantity();

                placeOrderDetailService.update(placeOrder, placeOrderDetails);

                Double afterQuantity = result.getItem().getQuantity();
                assertThat(result.getQuantity()).isEqualTo(UPDATE_QUANTITY);
                assertThat(afterQuantity - beforeQuantity).isEqualTo(UPDATE_QUANTITY - QUANTITY);
            }
        }
    }
}
