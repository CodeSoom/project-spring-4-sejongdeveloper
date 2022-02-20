package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;
import com.querydsl.core.QueryResults;

public interface PlaceOrderRepositoryCustom {
    QueryResults<PlaceOrder> findAll(PlaceOrderSearchCondition condition);
}
