package com.codesoom.sejongdeveloper.errors;

public class ReleaseOrderNoutFoundException extends RuntimeException {
    public ReleaseOrderNoutFoundException(Long id) {
        super(String.format("[%d] 출고를 찾을 수 없습니다.\n" +
                "출고의 일련번호를 확인하세요.", id));
    }
}
