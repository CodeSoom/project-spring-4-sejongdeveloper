package com.codesoom.sejongdeveloper.dto;

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
}
