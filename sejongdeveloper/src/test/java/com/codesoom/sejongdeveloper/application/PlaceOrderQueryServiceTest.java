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
    private static final int PLACE_ORDER_SIZE = 10;

    @Autowired
    private PlaceOrderQueryService placeOrderQueryService;

    @Autowired
    private PlaceOrderRepository placeOrderRepository;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < PLACE_ORDER_SIZE; i++) {
            Long id = (long) (i + 1);
            String name = PLACE_ORDER_NAME + i;
            LocalDate date = PLACE_ORDER_DATE.plusDays(i - 1);

            PlaceOrder placeOrder = getPlaceOrder(id, name, date);

            placeOrderRepository.save(placeOrder);
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class search_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_검색조건의_발주목록이_있는_경우 {
            private PlaceOrderSearchCondition nameCondition;
            private PlaceOrderSearchCondition dateCondition;

            @BeforeEach
            void setUp() {
                nameCondition = getCondition(PLACE_ORDER_NAME, null);
                dateCondition = getCondition(null, PLACE_ORDER_DATE);
            }

            @Test
            @DisplayName("발주목록 페이지를 리턴한다")
            void 발주목록_페이지를_리턴한다() {
                Page<PlaceOrderResponse> page = placeOrderQueryService.search(nameCondition);
                assertThat(page.getContent().size()).isEqualTo(PLACE_ORDER_SIZE);

                page = placeOrderQueryService.search(dateCondition);
                assertThat(page.getContent().size()).isOne();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_검색조건의_발주목록이_없는_경우 {
            private static final String NOT_PLACE_ORDER_NAME = "not name";
            private PlaceOrderSearchCondition condition;

            @BeforeEach
            void setUp() {
                condition = getCondition(NOT_PLACE_ORDER_NAME, PLACE_ORDER_DATE);
            }

            @Test
            @DisplayName("비어있는 발주목록 페이지를 리턴한다")
            void 비어있는_발주목록_페이지를_리턴한다() {
                Page<PlaceOrderResponse> page = placeOrderQueryService.search(condition);

                assertThat(page.getContent().size()).isZero();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 검색조건이_없는_경우 {
            private PlaceOrderSearchCondition condition;

            @BeforeEach
            void setUp() {
                condition = getCondition(null, null);
            }

            @Test
            @DisplayName("비어있는 발주목록 페이지를 리턴한다")
            void 비어있는_발주목록_페이지를_리턴한다() {
                Page<PlaceOrderResponse> page = placeOrderQueryService.search(condition);

                assertThat(page.getContent().size()).isEqualTo(PLACE_ORDER_SIZE);
            }
        }
    }

    private PageRequest getPageable() {
        return PageRequest.of(0, 10);
    }

    private PlaceOrderSearchCondition getCondition(String name, LocalDate date) {
        return PlaceOrderSearchCondition.builder()
                .name(name)
                .date(date)
                .pageable(getPageable())
                .build();
    }

    private PlaceOrder getPlaceOrder(Long id, String name, LocalDate date) {
        return PlaceOrder.builder()
                .id(id)
                .name(name)
                .date(date)
                .build();
    }
}
