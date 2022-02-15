package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailSaveRequest;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"InnerClassMaybeStatic", "NonAsciiCharacters"})
@DisplayName("PlaceOrderDetailService 클래스")
class PlaceOrderDetailServiceTest {

    private static final Long ITEM_ID = 1L;
    private PlaceOrderDetailService placeOrderDetailService;
    private PlaceOrderDetailRepository placeOrderDetailRepository = mock(PlaceOrderDetailRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeEach
    void setUp() {
        placeOrderDetailService = new PlaceOrderDetailService(placeOrderDetailRepository, itemRepository);

        Item item = Item.builder().build();

        given(itemRepository.findById(ITEM_ID)).willReturn(Optional.of(item));
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class savePlaceOrderDetails_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유효한_파라미터인_경우 {
            private PlaceOrder placeOrder;
            private List<PlaceOrderDetailSaveRequest> placeOrderDetails;

            @BeforeEach
            void setUp() {
                placeOrder = PlaceOrder.builder().build();

                PlaceOrderDetailSaveRequest request = PlaceOrderDetailSaveRequest.builder()
                        .itemId(ITEM_ID)
                        .quantity(1_000.0)
                        .build();

                placeOrderDetails = List.of(request);
            }

            @Test
            @DisplayName("발주상세를 저장한다")
            void 발주상세를_저장한다() {
                placeOrderDetailService.savePlaceOrderDetails(placeOrder, placeOrderDetails);
            }
        }
    }
}