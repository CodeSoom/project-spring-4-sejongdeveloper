package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static com.codesoom.sejongdeveloper.domain.QPlaceOrder.placeOrder;
import static org.springframework.util.StringUtils.hasText;

public class PlaceOrderRepositoryImpl implements PlaceOrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlaceOrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public QueryResults<PlaceOrder> findAll(PlaceOrderSearchCondition condition, Pageable pageable) {
        return queryFactory
                .selectFrom(placeOrder)
                .where(nameLike(condition.getName()),
                        startDateGoe(condition.getStartDate()),
                        endDateLoe(condition.getEndDate()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
    }

    private BooleanExpression endDateLoe(LocalDate endDate) {
        return endDate != null ? placeOrder.date.loe(endDate) : null;
    }

    private BooleanExpression startDateGoe(LocalDate startDate) {
        return startDate != null ? placeOrder.date.goe(startDate) : null;
    }

    private BooleanExpression nameLike(String name) {
        return hasText(name) ? placeOrder.name.contains(name) : null;
    }

}
