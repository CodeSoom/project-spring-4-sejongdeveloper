package com.codesoom.sejongdeveloper.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReleaseOrderUpdateRequest {

    private String name;    //출고명

    private LocalDate date; //출고날짜

}
