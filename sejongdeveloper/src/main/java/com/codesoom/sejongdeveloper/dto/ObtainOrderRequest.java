package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ObtainOrderRequest {

    @NotBlank
    private String name;

    private LocalDate date;

    @NotNull
    @Valid
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
