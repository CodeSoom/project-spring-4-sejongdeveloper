package com.codesoom.sejongdeveloper.errors;

public class ReleaseOrderDetailNotFoundException extends RuntimeException {
    public ReleaseOrderDetailNotFoundException(Long id) {
        super(String.format("[%d] 출고상세를 찾을 수 없습니다.\n" +
                "출고상세의 일련번호를 확인하세요.", id));
    }
}
