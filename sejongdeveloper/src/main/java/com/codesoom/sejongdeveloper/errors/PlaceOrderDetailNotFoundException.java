package com.codesoom.sejongdeveloper.errors;

public class PlaceOrderDetailNotFoundException extends RuntimeException {
    public PlaceOrderDetailNotFoundException(Long id) {
        super(String.format("[%d] 발주상세를 찾을 수 없습니다.\n" +
                "발주상세의 일련번호를 확인하세요.", id));
    }
}
