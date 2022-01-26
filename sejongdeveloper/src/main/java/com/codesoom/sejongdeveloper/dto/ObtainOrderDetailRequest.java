package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
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

    public ObtainOrderDetail createObtainOrderDetail(Item item) {
        return ObtainOrderDetail.builder()
                .item(item)
                .quantity(quantity)
                .build();
    }
}
