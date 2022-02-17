package com.codesoom.sejongdeveloper.domain;

import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "place_order")   //발주
public class PlaceOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_order_id")
    private Long id;    //발주 일련번호

    private String name;    //발주명

    private LocalDate date; //발주일

    @Builder
    public PlaceOrder(Long id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public PlaceOrder(PlaceOrderSaveRequest request) {
        this.id = request.getId();
        this.name = request.getName();
        this.date = request.getDate();
    }
}
