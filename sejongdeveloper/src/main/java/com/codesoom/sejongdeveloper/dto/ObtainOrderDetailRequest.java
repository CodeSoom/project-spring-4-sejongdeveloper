package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class ObtainOrderDetailRequest {

    @NotNull
    private Long itemId;

    @NotNull
    private Double quantity;

    @Builder
    public ObtainOrderDetailRequest(Long itemId, Double quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public ObtainOrderDetail createObtainOrderDetail(ObtainOrder obtainOrder, Item item) {
        return ObtainOrderDetail.builder()
                .obtainOrder(obtainOrder)
                .item(item)
                .quantity(quantity)
                .build();
    }
}
