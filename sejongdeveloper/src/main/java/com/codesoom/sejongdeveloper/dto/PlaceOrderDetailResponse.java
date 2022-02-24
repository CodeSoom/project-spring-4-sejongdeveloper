package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceOrderDetailResponse {

    private Long id;    //발주상세 일련번호

    private PlaceOrderResponse placeOrder;

    private ItemResponse item;  //상품

    private Double quantity;    //발주수량

    @Builder
    public PlaceOrderDetailResponse(Long id, PlaceOrderResponse placeOrder, ItemResponse item, Double quantity) {
        this.id = id;
        this.placeOrder = placeOrder;
        this.item = item;
        this.quantity = quantity;
    }
}
