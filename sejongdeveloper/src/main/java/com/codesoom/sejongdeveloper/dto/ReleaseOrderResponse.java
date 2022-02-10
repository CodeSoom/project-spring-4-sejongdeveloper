package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ReleaseOrderResponse {

    private Long id;    //출고 일련번호

    private String name;    //출고명

    private LocalDate date; //출고날짜

    public ReleaseOrderResponse(ReleaseOrder releaseOrder) {
        this.id = releaseOrder.getId();
        this.name = releaseOrder.getName();
        this.date = releaseOrder.getDate();
    }
}
