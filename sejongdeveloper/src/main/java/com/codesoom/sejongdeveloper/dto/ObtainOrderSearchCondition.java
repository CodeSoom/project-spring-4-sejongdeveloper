package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ObtainOrderSearchCondition {
    private String name;    //수주명

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;    //시작수주날짜

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;  //마지막수주날짜

    @Builder
    public ObtainOrderSearchCondition(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
