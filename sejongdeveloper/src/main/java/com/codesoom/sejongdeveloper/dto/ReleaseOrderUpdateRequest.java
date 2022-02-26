package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReleaseOrderUpdateRequest {

    private String name;    //출고명

    private LocalDate date; //출고날짜

    private List<ReleaseOrderDetailUpdateRequest> releaseOrderDetails;    //출고상세 목록

    @Builder
    public ReleaseOrderUpdateRequest(String name, LocalDate date, List<ReleaseOrderDetailUpdateRequest> releaseOrderDetails) {
        this.name = name;
        this.date = date;
        this.releaseOrderDetails = releaseOrderDetails;
    }
}
