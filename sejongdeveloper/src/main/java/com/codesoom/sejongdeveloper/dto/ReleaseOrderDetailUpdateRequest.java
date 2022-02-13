package com.codesoom.sejongdeveloper.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReleaseOrderDetailUpdateRequest {

    private Long id;    //출고 상세 일련번호

    private BigDecimal quantity;    //출고수량

}
