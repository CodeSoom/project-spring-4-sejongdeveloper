package com.codesoom.sejongdeveloper.domain;

import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailUpdateRequest;
import com.codesoom.sejongdeveloper.errors.ItemNotEnoughException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "release_order_detail")   //출고 상세
public class ReleaseOrderDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "release_order_detail_id")
    private Long id;    //출고 상세 일련번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "release_order_id")
    private ReleaseOrder releaseOrder;  //출고

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obtain_order_detail_id")
    private ObtainOrderDetail obtainOrderDetail;    //수주 상세

    private Double quantity;    //출고수량

    public ReleaseOrderDetail(Long id) {
        this.id = id;
    }

    @Builder
    public ReleaseOrderDetail(Long id,
                              ReleaseOrder releaseOrder,
                              ObtainOrderDetail obtainOrderDetail,
                              Double quantity) {
        this.obtainOrderDetail = obtainOrderDetail;
        this.id = id;
        this.releaseOrder = releaseOrder;
        this.quantity = quantity;
    }

    public static ReleaseOrderDetail createReleaseOrderDetail(
            ReleaseOrderDetailSaveRequest request,
            ReleaseOrder releaseOrder,
            ObtainOrderDetail obtainOrderDetail
    ) {
        Double itemQuantity = obtainOrderDetail.getItem().getQuantity();

        if (request.getQuantity() > itemQuantity) {
            throw new ItemNotEnoughException("출고수량", request.getQuantity());
        }

        obtainOrderDetail.getItem().plusQuantity(-1 * request.getQuantity());

        return ReleaseOrderDetail.builder()
                .releaseOrder(releaseOrder)
                .obtainOrderDetail(obtainOrderDetail)
                .quantity(request.getQuantity())
                .build();
    }

    public void update(ReleaseOrderDetailUpdateRequest request) {
        validItemQuantity(request);

        plusItemQuantity(request);

        this.quantity = request.getQuantity();
    }

    private void validItemQuantity(ReleaseOrderDetailUpdateRequest request) {
        Double itemQuantity = obtainOrderDetail.getItem().getQuantity();

        if (request.getQuantity() > itemQuantity) {
            throw new ItemNotEnoughException("출고수량", quantity);
        }
    }

    private void plusItemQuantity(ReleaseOrderDetailUpdateRequest request) {
        Double changeQuantity = this.quantity - request.getQuantity();

        obtainOrderDetail.getItem().plusQuantity(changeQuantity);
    }
}
