package com.codesoom.sejongdeveloper.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ObtainOrderResponse {

    private Long id;    //수주 일련번호

    private String name;    //수주명

    private LocalDate date; //수주날짜

    private List<ObtainOrderDetailResponse> obtainOrderDetails; //수주상세 목록

    @Builder
    public ObtainOrderResponse(Long id, String name, LocalDate date, List<ObtainOrderDetailResponse> obtainOrderDetails) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.obtainOrderDetails = obtainOrderDetails;
    }
}
