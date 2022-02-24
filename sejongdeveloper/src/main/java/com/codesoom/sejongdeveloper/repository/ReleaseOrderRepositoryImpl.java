package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.time.LocalDate;

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
                .where(nameLike(condition.getName()),
                        startDateGoe(condition.getStartDate()),
                        endDateLoe(condition.getEndDate()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(releaseOrder.id.desc())
                .fetchResults();
    }

    private BooleanExpression endDateLoe(LocalDate endDate) {
        return endDate != null ? releaseOrder.date.loe(endDate) : null;
    }

    private BooleanExpression startDateGoe(LocalDate startDate) {
        return startDate != null ? releaseOrder.date.goe(startDate) : null;
    }

    private BooleanExpression nameLike(String name) {
        return hasText(name) ? releaseOrder.name.contains(name) : null;
    }
}
