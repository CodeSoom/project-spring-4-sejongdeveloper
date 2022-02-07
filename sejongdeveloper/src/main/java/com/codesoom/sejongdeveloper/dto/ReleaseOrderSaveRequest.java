package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
public class ReleaseOrderSaveRequest {

    @NotBlank(message = "출고명을 입력하세요.")
    private String name;    //출고명

    private LocalDate date; //출고날짜

    @Builder
    public ReleaseOrderSaveRequest(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }
}
