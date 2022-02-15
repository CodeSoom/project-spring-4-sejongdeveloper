package com.codesoom.sejongdeveloper.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReleaseOrderDetailUpdateRequest {

    private Long id;    //출고 상세 일련번호

    private Double quantity;    //출고수량

}
