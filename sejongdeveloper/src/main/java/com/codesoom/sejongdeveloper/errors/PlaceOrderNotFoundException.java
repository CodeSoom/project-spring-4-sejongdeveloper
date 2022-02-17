package com.codesoom.sejongdeveloper.errors;

public class PlaceOrderNotFoundException extends RuntimeException {
    public PlaceOrderNotFoundException(Long id) {
        super(String.format("[%d] 발주를 찾을 수 없습니다.\n" +
                "발주의 일련번호를 확인하세요.", id));
    }
}
