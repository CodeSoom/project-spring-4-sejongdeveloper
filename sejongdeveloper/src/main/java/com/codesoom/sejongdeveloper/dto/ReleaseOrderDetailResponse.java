package com.codesoom.sejongdeveloper.dto;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.domain.ReleaseOrderDetail;
import lombok.Getter;

@Getter
public class ReleaseOrderDetailResponse {

    private Long id;    //출고 상세 일련번호

    private ObtainOrderDetailResponse obtainOrderDetail;    //수주 상세

    private Double quantity;    //출고수량

    public ReleaseOrderDetailResponse(ReleaseOrderDetail releaseOrderDetail) {
        this.id = releaseOrderDetail.getId();
        this.obtainOrderDetail = getObtainOrderDetailResponse(releaseOrderDetail.getObtainOrderDetail());
        this.quantity = releaseOrderDetail.getQuantity();
    }

    private ObtainOrderDetailResponse getObtainOrderDetailResponse(ObtainOrderDetail entity) {
        if (entity == null) {
            return null;
        }

        return ObtainOrderDetailResponse.builder()
                .item(getItemResponse(entity.getItem()))
                .build();
    }

    private ItemResponse getItemResponse(Item entity) {
        if (entity == null) {
            return null;
        }

        return ItemResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
