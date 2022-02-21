package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ObtainOrderSearchCondition {
    private String name;    //수주명

    private LocalDate startDate;    //시작수주날짜

    private LocalDate endDate;  //마지막수주날짜

    @Builder
    public ObtainOrderSearchCondition(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
