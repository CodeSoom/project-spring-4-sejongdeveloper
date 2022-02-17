package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.domain.PlaceOrderDetail;
import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailUpdateRequest;
import com.codesoom.sejongdeveloper.errors.PlaceOrderDetailNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"InnerClassMaybeStatic", "NonAsciiCharacters"})
@DisplayName("PlaceOrderDetailService 클래스")
class PlaceOrderDetailServiceTest {

    private static final Long ITEM_ID = 1L;
    private static final Long VALID_PLACE_ORDER_DETAIL_ID = 1L;
    private static final Double QUANTITY = 1_000.0;
    private static final Long INVALID_PLACE_ORDER_DETAIL_ID = 2L;
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

        PlaceOrderDetail placeOrderDetail = PlaceOrderDetail.builder()
                .id(VALID_PLACE_ORDER_DETAIL_ID)
                .item(item)
                .quantity(QUANTITY)
                .build();

        given(placeOrderDetailRepository.findById(VALID_PLACE_ORDER_DETAIL_ID)).willReturn(Optional.of(placeOrderDetail));
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
        private List<PlaceOrderDetailUpdateRequest> placeOrderDetails;

        @BeforeEach
        void setUp() {
            PlaceOrderDetailUpdateRequest request = PlaceOrderDetailUpdateRequest.builder()
                    .id(VALID_PLACE_ORDER_DETAIL_ID)
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
                PlaceOrderDetail result = placeOrderDetailRepository.findById(VALID_PLACE_ORDER_DETAIL_ID).get();
                Double beforeQuantity = result.getItem().getQuantity();

                placeOrderDetailService.update(placeOrderDetails);

                Double afterQuantity = result.getItem().getQuantity();
                assertThat(result.getQuantity()).isEqualTo(UPDATE_QUANTITY);
                assertThat(afterQuantity - beforeQuantity).isEqualTo(UPDATE_QUANTITY - QUANTITY);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_발주상세를_찾지_못한_경우 {
            @BeforeEach
            void setUp() {
                PlaceOrderDetailUpdateRequest request = PlaceOrderDetailUpdateRequest.builder()
                        .id(INVALID_PLACE_ORDER_DETAIL_ID)
                        .itemId(ITEM_ID)
                        .quantity(UPDATE_QUANTITY)
                        .build();

                placeOrderDetails = List.of(request);
            }

            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> placeOrderDetailService.update(placeOrderDetails))
                        .isInstanceOf(PlaceOrderDetailNotFoundException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 이미_발주상세를_수정한_경우 {
            private final Double SECOND_UPDATE_QUANTITY = 2_000.0;
            private List<PlaceOrderDetailUpdateRequest> secondPlaceOrderDetails;


            @BeforeEach
            void setUp() {
                PlaceOrderDetailUpdateRequest request = PlaceOrderDetailUpdateRequest.builder()
                        .id(VALID_PLACE_ORDER_DETAIL_ID)
                        .itemId(ITEM_ID)
                        .quantity(SECOND_UPDATE_QUANTITY )
                        .build();

                secondPlaceOrderDetails = List.of(request);
            }

            @Test
            @DisplayName("이전 발주수량 차이만큼 상품수량을 반영한다")
            void 이전_발주수량_차이만큼_상품수량을_반영한다() {
                PlaceOrderDetail result = placeOrderDetailRepository.findById(VALID_PLACE_ORDER_DETAIL_ID).get();

                placeOrderDetailService.update(placeOrderDetails);
                Double beforeQuantity = result.getItem().getQuantity();

                placeOrderDetailService.update(secondPlaceOrderDetails);
                Double afterQuantity = result.getItem().getQuantity();

                assertThat(afterQuantity - beforeQuantity).isEqualTo(SECOND_UPDATE_QUANTITY - UPDATE_QUANTITY);
            }
        }
    }
}
