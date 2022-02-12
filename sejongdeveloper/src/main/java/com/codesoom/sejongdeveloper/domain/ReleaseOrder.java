package com.codesoom.sejongdeveloper.domain;

import com.codesoom.sejongdeveloper.dto.ReleaseOrderUpdateRequest;
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
@Table(name = "release_order")  //출고
public class ReleaseOrder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "release_order_id")
    private Long id;    //출고 일련번호

    private String name;    //출고명

    private LocalDate date; //출고날짜

    @Builder
    public ReleaseOrder(Long id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public void update(ReleaseOrderUpdateRequest request) {

    }
}
