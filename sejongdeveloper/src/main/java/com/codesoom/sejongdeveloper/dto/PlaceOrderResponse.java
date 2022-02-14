package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PlaceOrderResponse {

    private Long id;    //발주 일련번호

    private String name;    //발주명

    private LocalDate date; //발주일

    public PlaceOrderResponse(PlaceOrder placeOrder) {
        this.id = placeOrder.getId();
        this.name = placeOrder.getName();
        this.date = placeOrder.getDate();
    }

}
