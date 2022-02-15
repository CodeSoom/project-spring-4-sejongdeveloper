package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReleaseOrderDetailSaveRequest {

    private Long obtainOrderDetailId;    //수주 상세 일련번호

    private Double quantity;    //출고수량

    @Builder
    public ReleaseOrderDetailSaveRequest(Long obtainOrderDetailId, Double quantity) {
        this.obtainOrderDetailId = obtainOrderDetailId;
        this.quantity = quantity;
    }
}
