package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderResponse;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@SpringBootTest
@Transactional
public class PlaceOrderQueryServiceTest {
    private static final Long PLACE_ORDER_ID = 1L;
    private static final String PLACE_ORDER_NAME = "test name";
    private static final LocalDate PLACE_ORDER_DATE = LocalDate.now();

    @Autowired
    private PlaceOrderQueryService placeOrderQueryService;

    @Autowired
    private PlaceOrderRepository placeOrderRepository;

    @BeforeEach
    void setUp() {
        PlaceOrder placeOrder = PlaceOrder.builder()
                .id(PLACE_ORDER_ID)
                .name(PLACE_ORDER_NAME)
                .date(PLACE_ORDER_DATE)
                .build();

        placeOrderRepository.save(placeOrder);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class search_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_검색조건의_발주목록이_있는_경우 {
            @Test
            @DisplayName("발주목록 페이지를 리턴한다")
            void 발주목록_페이지를_리턴한다() {
                Pageable pageable = PageRequest.of(0, 10);

                PlaceOrderSearchCondition condition = PlaceOrderSearchCondition.builder()
                        .name(PLACE_ORDER_NAME)
                        .date(PLACE_ORDER_DATE)
                        .pageable(pageable)
                        .build();

                Page<PlaceOrderResponse> page = placeOrderQueryService.search(condition);

                assertThat(page.getContent().size()).isEqualTo(1);
            }
        }
    }
}
