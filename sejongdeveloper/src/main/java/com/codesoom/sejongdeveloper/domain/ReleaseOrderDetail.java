package com.codesoom.sejongdeveloper.domain;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        Double itemQuantity = obtainOrderDetail.getItem().getQuantity();

        if (quantity > itemQuantity) {
            throw new ItemNotEnoughException("출고수량", quantity);
        }

        this.obtainOrderDetail = obtainOrderDetail;
        this.id = id;
        this.releaseOrder = releaseOrder;
        this.quantity = quantity;
    }

    public void update(ReleaseOrderDetailUpdateRequest request) {
        Double itemQuantity = obtainOrderDetail.getItem().getQuantity();

        if (request.getQuantity() > itemQuantity) {
            throw new ItemNotEnoughException("출고수량", quantity);
        }

        this.quantity = request.getQuantity();
    }
}
