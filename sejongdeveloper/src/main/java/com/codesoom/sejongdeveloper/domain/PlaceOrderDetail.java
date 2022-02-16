package com.codesoom.sejongdeveloper.domain;

import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailSaveRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Table(name = "place_order_detail")   //발주상세
public class PlaceOrderDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //발주상세 일련번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_order_id")
    private PlaceOrder placeOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;  //상품

    private Double quantity;    //발주개수

    @Builder
    public PlaceOrderDetail(PlaceOrderDetailSaveRequest request, PlaceOrder placeOrder, Item item) {
        this.id = request.getId();
        this.placeOrder = placeOrder;
        this.item = item;
        this.quantity = request.getQuantity();

        item.plusQuantity(quantity);
    }

}
