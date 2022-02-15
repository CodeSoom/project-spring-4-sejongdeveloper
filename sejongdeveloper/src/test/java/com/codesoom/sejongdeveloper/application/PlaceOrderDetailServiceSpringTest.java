package com.codesoom.sejongdeveloper.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SuppressWarnings({"InnerClassMaybeStatic", "NonAsciiCharacters"})
@DisplayName("PlaceOrderService 클래스")
public class PlaceOrderDetailServiceSpringTest {

    @Autowired
    private PlaceOrderService placeOrderService;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class completePlaceOrder_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_발주를_찾은_경우 {
            @Test
            @DisplayName("발주수량을 상품수량에 반영한다")
            void 발주수량을_상품수량에_반영한다() {

            }
        }
    }



}
