package com.codesoom.sejongdeveloper.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReleaseOrderUpdateRequest {

    private String name;    //출고명

    private LocalDate date; //출고날짜

    private List<ReleaseOrderDetailUpdateRequest> releaseOrderDetails;    //출고상세 목록

}
