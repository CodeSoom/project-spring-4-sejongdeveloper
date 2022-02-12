package com.codesoom.sejongdeveloper.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReleaseOrderUpdateRequest {

    private Long id;    //출고 일련번호

    private String name;    //출고명

    private LocalDate date; //출고날짜

}
