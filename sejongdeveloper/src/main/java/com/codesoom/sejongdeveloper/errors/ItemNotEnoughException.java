package com.codesoom.sejongdeveloper.errors;

public class ItemNotEnoughException extends RuntimeException {
    public ItemNotEnoughException(String target, Double quantity) {
        super(String.format("상품수량이 부족합니다.\n" +
                "[%s]에 대한 수량[%f]을 확인하세요.", target, quantity));
    }
}
