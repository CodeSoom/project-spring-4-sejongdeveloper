package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObtainOrderDetailResponse {

    private Long id;    //수주 상세 일련번호

    private ObtainOrder obtainOrder;    //수주

    private Item item;  //상품

    private BigDecimal quantity;    //수주 수량

}
