package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReleaseOrderDetailUpdateRequest {

    private Long id;    //출고 상세 일련번호

    private Double quantity;    //출고수량

    @Builder
    public ReleaseOrderDetailUpdateRequest(Long id, Double quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
