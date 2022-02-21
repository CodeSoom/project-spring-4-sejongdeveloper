package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.dto.ObtainOrderSearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static com.codesoom.sejongdeveloper.domain.QObtainOrder.obtainOrder;
import static org.springframework.util.StringUtils.hasText;

public class ObtainOrderRepositoryImpl implements ObtainOrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ObtainOrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public QueryResults<ObtainOrder> findAll(ObtainOrderSearchCondition condition, Pageable pageable) {
        return queryFactory
                .selectFrom(obtainOrder)
                .where(nameLike(condition.getName()),
                        startDateGoe(condition.getStartDate()),
                        endDateLoe(condition.getEndDate()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
    }

    private BooleanExpression endDateLoe(LocalDate endDate) {
        return endDate != null ? obtainOrder.date.loe(endDate) : null;
    }

    private BooleanExpression startDateGoe(LocalDate startDate) {
        return startDate != null ? obtainOrder.date.goe(startDate) : null;
    }

    private BooleanExpression nameLike(String name) {
        return hasText(name) ? obtainOrder.name.contains(name) : null;
    }

}
