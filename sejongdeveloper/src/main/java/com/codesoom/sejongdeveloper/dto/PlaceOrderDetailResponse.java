package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceOrderDetailResponse {

    private Long id;    //발주상세 일련번호

    private PlaceOrder placeOrder;

    private Item item;  //상품

    private Double quantity;    //발주수량

    @Builder
    public PlaceOrderDetailResponse(Long id, PlaceOrder placeOrder, Item item, Double quantity) {
        this.id = id;
        this.placeOrder = placeOrder;
        this.item = item;
        this.quantity = quantity;
    }
}
