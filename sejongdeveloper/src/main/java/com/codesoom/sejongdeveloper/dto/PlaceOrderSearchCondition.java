package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Getter
public class PlaceOrderSearchCondition {
    private String name;    //발주명

    private LocalDate date; //발주일

    @Builder
    public PlaceOrderSearchCondition(String name, LocalDate date, Pageable pageable) {
        this.name = name;
        this.date = date;
    }
}
