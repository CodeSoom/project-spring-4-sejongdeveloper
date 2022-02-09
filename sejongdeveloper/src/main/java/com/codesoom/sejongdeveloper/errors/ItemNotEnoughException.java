package com.codesoom.sejongdeveloper.errors;

import java.math.BigDecimal;

public class ItemNotEnoughException extends RuntimeException {
    public ItemNotEnoughException(String target, BigDecimal quantity) {
        super(String.format("상품수량이 부족합니다.\n" +
                "[%s]에 대한 수량[%s]을 확인하세요.", target, quantity));
    }
}
