package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import static com.codesoom.sejongdeveloper.domain.QReleaseOrder.releaseOrder;
import static org.springframework.util.StringUtils.hasText;

public class ReleaseOrderRepositoryImpl implements ReleaseOrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReleaseOrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public QueryResults<ReleaseOrder> search(ReleaseOrderSearchCondition condition, Pageable pageable) {
        return queryFactory
                .selectFrom(releaseOrder)
                .where(nameLike(condition.getName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
    }

    private BooleanExpression nameLike(String name) {
        return hasText(name) ? releaseOrder.name.contains(name) : null;
    }
}
