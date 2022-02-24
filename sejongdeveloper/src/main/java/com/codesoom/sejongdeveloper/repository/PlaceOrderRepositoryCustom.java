package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

public interface PlaceOrderRepositoryCustom {
    QueryResults<PlaceOrder> findAll(PlaceOrderSearchCondition condition, Pageable pageable);
}
