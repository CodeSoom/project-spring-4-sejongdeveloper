package com.codesoom.sejongdeveloper.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@Table(name = "obtain_order")   //수주
public class ObtainOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "obtain_order_id")
    private Long id;    //수주 일련번호

    private String name;    //수주명

    private LocalDate date; //수주날짜

    @Builder
    public ObtainOrder(Long id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public void update(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

}
