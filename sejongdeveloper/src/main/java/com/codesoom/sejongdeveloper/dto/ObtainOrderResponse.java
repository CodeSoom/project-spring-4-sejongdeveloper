package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObtainOrderResponse {

    private Long id;    //수주 일련번호

    private String name;    //수주명

    private LocalDate date; //수주날짜

    private List<ObtainOrderDetailResponse> obtainOrderDetails; //수주상세 목록

}
