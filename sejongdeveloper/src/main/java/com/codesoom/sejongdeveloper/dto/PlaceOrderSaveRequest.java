package com.codesoom.sejongdeveloper.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PlaceOrderSaveRequest {

    private Long id;    //발주 일련번호

    private String name;    //발주명

    private LocalDate date; //발주일

    List<PlaceOrderDetailSaveRequest> placeOrderDetails;    //발주상세 목록

}
