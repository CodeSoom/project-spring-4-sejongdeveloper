package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PlaceOrderResponse {

    private Long id;    //발주 일련번호

    private String name;    //발주명

    private LocalDate date; //발주일

    @Builder
    public PlaceOrderResponse(Long id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
}
