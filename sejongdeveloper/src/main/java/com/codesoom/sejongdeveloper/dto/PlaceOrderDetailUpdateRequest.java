package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceOrderDetailUpdateRequest {

    private Long id;    //발주상세 일련번호

    private Long itemId;  //상품 일련번호

    private Double quantity;    //발주수량

    @Builder
    public PlaceOrderDetailUpdateRequest(Long id, Long itemId, Double quantity) {
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
