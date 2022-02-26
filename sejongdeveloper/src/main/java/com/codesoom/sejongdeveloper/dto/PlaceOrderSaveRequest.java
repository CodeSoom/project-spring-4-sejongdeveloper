package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlaceOrderSaveRequest {

    private Long id;    //발주 일련번호

    private String name;    //발주명

    private LocalDate date; //발주일

    List<PlaceOrderDetailSaveRequest> placeOrderDetails;    //발주상세 목록

    @Builder
    public PlaceOrderSaveRequest(Long id, String name, LocalDate date, List<PlaceOrderDetailSaveRequest> placeOrderDetails) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.placeOrderDetails = placeOrderDetails;
    }
}
