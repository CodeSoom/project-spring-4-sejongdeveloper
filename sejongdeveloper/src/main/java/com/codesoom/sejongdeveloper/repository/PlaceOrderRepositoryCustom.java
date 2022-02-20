package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;

import java.util.List;

public interface PlaceOrderRepositoryCustom {
    List<PlaceOrder> findAll(PlaceOrderSearchCondition condition);
}
