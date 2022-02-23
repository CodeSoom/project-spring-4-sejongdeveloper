package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Getter
public class ReleaseOrderSearchCondition {
    private String name;    //출고명

    private LocalDate startDate;    //시작 출고일

    private LocalDate endDate;  //종료 출고일

    @Builder
    public ReleaseOrderSearchCondition(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
