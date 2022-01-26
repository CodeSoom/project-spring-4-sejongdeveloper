package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ObtainOrderDetailRequest {

    private Long itemId;

    private BigDecimal quantity;

    @Builder
    public ObtainOrderDetailRequest(Long itemId, BigDecimal quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }
}
