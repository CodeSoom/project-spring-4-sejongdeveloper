package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemResponse {

    private Long id;    //상품 일련번호

    private String code;    //상품코드

    private String name;    //상품명

    private Double quantity;    //상품수량

    @Builder
    public ItemResponse(Long id, String code, String name, Double quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

}
