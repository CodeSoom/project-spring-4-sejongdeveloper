package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.domain.PlaceOrderDetail;
import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.repository.ItemRepository;
import com.codesoom.sejongdeveloper.repository.PlaceOrderDetailRepository;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SuppressWarnings({"InnerClassMaybeStatic", "NonAsciiCharacters"})
@DisplayName("PlaceOrderService 클래스")
@Transactional
public class PlaceOrderDetailServiceSpringTest {

    private static final Long PLACE_ORDER_ID = 1L;

    @Autowired
    private PlaceOrderService placeOrderService;

    @Autowired
    private PlaceOrderRepository placeOrderRepository;

    @Autowired
    private PlaceOrderDetailRepository placeOrderDetailRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class completePlaceOrder_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_발주를_찾은_경우 {
            private Item item;
            private final Double QUANTITY = 1_000.0;

            @BeforeEach
            void setUp() {
                PlaceOrder placeOrder = PlaceOrder.builder().build();
                placeOrderRepository.save(placeOrder);

                item = Item.builder().build();
                itemRepository.save(item);

                PlaceOrderDetailSaveRequest request = PlaceOrderDetailSaveRequest.builder()
                        .quantity(QUANTITY)
                        .build();

                PlaceOrderDetail placeOrderDetail = PlaceOrderDetail.builder()
                        .placeOrder(placeOrder)
                        .item(item)
                        .request(request)
                        .build();

                placeOrderDetailRepository.save(placeOrderDetail);
            }

            @Test
            @DisplayName("발주수량을 상품수량에 반영한다")
            void 발주수량을_상품수량에_반영한다() {
                Double beforeQuantity = item.getQuantity();

                placeOrderService.completePlaceOrder(PLACE_ORDER_ID);

                Double afterQuantity = item.getQuantity();

                assertThat(afterQuantity - beforeQuantity).isEqualTo(QUANTITY);
            }
        }
    }



}
