package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.domain.QObtainOrderDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.codesoom.sejongdeveloper.domain.QObtainOrderDetail.*;

public class ObtainOrderDetailRepositoryImpl implements ObtainOrderDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ObtainOrderDetailRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ObtainOrderDetail> findAllByObtainOrderId(Long obtainOrderId) {
        return queryFactory
                .selectFrom(obtainOrderDetail)
                .where(obtainOrderDetail.obtainOrder.id.eq(obtainOrderId))
                .fetch();
    }
}
