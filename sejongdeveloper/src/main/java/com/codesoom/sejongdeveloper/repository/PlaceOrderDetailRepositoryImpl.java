package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.PlaceOrderDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.codesoom.sejongdeveloper.domain.QPlaceOrderDetail.placeOrderDetail;

public class PlaceOrderDetailRepositoryImpl implements PlaceOrderDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlaceOrderDetailRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PlaceOrderDetail> findAllByPlaceOrderId(Long placeOrderId) {
        return queryFactory
                .selectFrom(placeOrderDetail)
                .where(placeOrderDetail.placeOrder.id.eq(placeOrderId))
                .fetch();
    }
}
