package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ObtainOrderDetailResponse {

    private Long id;    //수주 상세 일련번호

    private ObtainOrderResponse obtainOrder;    //수주

    private Item item;  //상품

    private BigDecimal quantity;    //수주 수량

    @Builder
    public ObtainOrderDetailResponse(Long id, ObtainOrderResponse obtainOrder, Item item, BigDecimal quantity) {
        this.id = id;
        this.obtainOrder = obtainOrder;
        this.item = item;
        this.quantity = quantity;
    }
}
