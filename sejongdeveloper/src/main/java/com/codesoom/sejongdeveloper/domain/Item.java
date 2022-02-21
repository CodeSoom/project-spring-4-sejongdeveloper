package com.codesoom.sejongdeveloper.domain;

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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "item")   //상품
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "item_id")
    private Long id;    //상품 일련번호

    private String code;    //상품코드

    private String name;    //상품명

    private Double quantity;    //상품수량

    @Builder
    public Item(Long id, String code, String name, Double quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public void plusQuantity(Double quantity) {
        if (this.quantity == null) {
            this.quantity = 0.0;
        }

        this.quantity += quantity;
    }
}
