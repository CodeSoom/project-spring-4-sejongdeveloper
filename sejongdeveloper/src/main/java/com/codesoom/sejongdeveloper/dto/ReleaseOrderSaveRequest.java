package com.codesoom.sejongdeveloper.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReleaseOrderSaveRequest {

    @NotBlank(message = "출고명을 입력하세요.")
    @Mapping
    private String name;    //출고명

    @Mapping
    private LocalDate date; //출고날짜

    @Size(min = 1, message = "출고상품이 존재하지 않습니다.")
    @NotNull
    @Valid
    private List<ReleaseOrderDetailSaveRequest> releaseOrderDetails;

    @Builder
    public ReleaseOrderSaveRequest(String name,
                                   LocalDate date,
                                   List<ReleaseOrderDetailSaveRequest> releaseOrderDetails) {

        this.name = name;
        this.date = date;
        this.releaseOrderDetails = releaseOrderDetails;
    }
}
