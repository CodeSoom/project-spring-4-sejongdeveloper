package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.QReleaseOrderDetail;
import com.codesoom.sejongdeveloper.domain.ReleaseOrderDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.codesoom.sejongdeveloper.domain.QReleaseOrderDetail.*;

public class ReleaseOrderDetailRepositoryImpl implements ReleaseOrderDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReleaseOrderDetailRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ReleaseOrderDetail> findAllByReleaseOrderId(Long releaseOrderId) {
        return queryFactory
                .selectFrom(releaseOrderDetail)
                .where(releaseOrderDetail.releaseOrder.id.eq(releaseOrderId))
                .fetch();
    }
}
