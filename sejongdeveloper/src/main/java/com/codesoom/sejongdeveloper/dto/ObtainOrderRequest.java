package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ObtainOrderRequest {

    private String name;

    private LocalDate date;

    private List<ObtainOrderDetailRequest> obtainOrderDetails;

    @Builder
    public ObtainOrderRequest(String name, LocalDate date, List<ObtainOrderDetailRequest> obtainOrderDetails) {
        this.name = name;
        this.date = date;
        this.obtainOrderDetails = obtainOrderDetails;
    }

    public ObtainOrder createObtainOrder() {
        return ObtainOrder.builder()
                .name(name)
                .date(date)
                .build();
    }

}
