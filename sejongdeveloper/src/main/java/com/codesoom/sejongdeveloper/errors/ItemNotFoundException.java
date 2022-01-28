package com.codesoom.sejongdeveloper.errors;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super(String.format("[%d] 상품을 찾을 수 없습니다.\n" +
                            "상품의 일련번호를 확인하세요.", id));
    }
}
