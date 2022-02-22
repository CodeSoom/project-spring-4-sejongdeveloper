package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ObtainOrderDetailResponse {

    private Long id;    //수주 상세 일련번호

    private ObtainOrderResponse obtainOrder;    //수주

    private ItemResponse item;  //상품

    private Double quantity;    //수주 수량

    private Boolean useYn;  //사용여부

    @Builder
    public ObtainOrderDetailResponse(Long id, ObtainOrderResponse obtainOrder, ItemResponse item, Double quantity, Boolean useYn) {
        this.id = id;
        this.obtainOrder = obtainOrder;
        this.item = item;
        this.quantity = quantity;
        this.useYn = useYn;
    }
}
