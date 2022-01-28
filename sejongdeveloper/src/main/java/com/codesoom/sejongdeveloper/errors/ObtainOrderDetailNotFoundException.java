package com.codesoom.sejongdeveloper.errors;

public class ObtainOrderDetailNotFoundException extends RuntimeException {

    public ObtainOrderDetailNotFoundException(Long id) {
        super(String.format("[%d] 수주상세를 찾을 수 없습니다.\n" +
                            "수주상세의 일련번호를 확인하세요.", id));
    }

}
