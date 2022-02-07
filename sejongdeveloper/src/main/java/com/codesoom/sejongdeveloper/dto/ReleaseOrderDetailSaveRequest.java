package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ReleaseOrderDetailSaveRequest {

    private Long obtainOrderDetailId;    //수주 상세 일련번호

    private BigDecimal quantity;    //출고수량

    @Builder
    public ReleaseOrderDetailSaveRequest(Long obtainOrderDetailId, BigDecimal quantity) {
        this.obtainOrderDetailId = obtainOrderDetailId;
        this.quantity = quantity;
    }
}
