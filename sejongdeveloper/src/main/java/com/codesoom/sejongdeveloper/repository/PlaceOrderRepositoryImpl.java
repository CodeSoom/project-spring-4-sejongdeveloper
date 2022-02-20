package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.codesoom.sejongdeveloper.domain.QPlaceOrder.placeOrder;
import static org.springframework.util.StringUtils.hasText;

public class PlaceOrderRepositoryImpl implements PlaceOrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlaceOrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public QueryResults<PlaceOrder> findAll(PlaceOrderSearchCondition condition) {
        return queryFactory
                .selectFrom(placeOrder)
                .where(nameLike(condition.getName()),
                        dateEq(condition.getDate()))
                .offset(condition.getPageable().getOffset())
                .limit(condition.getPageable().getPageSize())
                .fetchResults();
    }

    private BooleanExpression nameLike(String name) {
        return hasText(name) ? placeOrder.name.contains(name) : null;
    }

    private BooleanExpression dateEq(LocalDate date) {
        return date != null ? placeOrder.date.eq(date) : null;
    }
}
