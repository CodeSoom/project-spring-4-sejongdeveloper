package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceOrderDetailSaveRequest {

    private Long id;    //발주상세 일련번호

    private Long itemId;  //상품 일련번호

    private Double quantity;    //발주개수

    @Builder
    public PlaceOrderDetailSaveRequest(Long id, Long itemId, Double quantity) {
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
