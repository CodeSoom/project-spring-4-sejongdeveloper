package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class ReleaseOrderSearchCondition {
    private String name;    //출고명

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;    //시작 출고일

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;  //종료 출고일

    @Builder
    public ReleaseOrderSearchCondition(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
