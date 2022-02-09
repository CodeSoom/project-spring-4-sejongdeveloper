package com.codesoom.sejongdeveloper.errors;

public class ReleaseOrderDetailOverSize extends RuntimeException {
    public ReleaseOrderDetailOverSize(int standardSize, int targetSize) {
        super(String.format("출고상세 최고개수 [%d]개를 초과했습니다.\n" +
                "출고상세 개수 [%d]개를 확인하세요.", standardSize, targetSize));
    }
}
