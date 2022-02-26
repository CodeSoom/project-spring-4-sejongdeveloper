package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PlaceOrderSearchCondition {
    private String name;    //발주명

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; //시작 발주일

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; //마지막 발주일

    @Builder
    public PlaceOrderSearchCondition(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
