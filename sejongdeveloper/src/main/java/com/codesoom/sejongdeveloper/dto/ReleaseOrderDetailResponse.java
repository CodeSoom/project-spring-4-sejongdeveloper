package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.domain.ReleaseOrderDetail;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ReleaseOrderDetailResponse {

    private Long id;    //출고 상세 일련번호

    private ObtainOrderDetailResponse obtainOrderDetail;    //수주 상세

    private BigDecimal quantity;    //출고수량

    public ReleaseOrderDetailResponse(ReleaseOrderDetail releaseOrderDetail) {
        this.id = releaseOrderDetail.getId();
        this.obtainOrderDetail = getObtainOrderDetailResponse(releaseOrderDetail.getObtainOrderDetail());
        this.quantity = releaseOrderDetail.getQuantity();
    }

    private ObtainOrderDetailResponse getObtainOrderDetailResponse(ObtainOrderDetail source) {
        //TODO: 응답객체 리펙토링할 때 반영할 것

        return null;
    }
}
