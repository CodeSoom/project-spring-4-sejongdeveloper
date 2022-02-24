package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.PlaceOrderDetail;

import java.util.List;

public interface PlaceOrderDetailRepositoryCustom {
    List<PlaceOrderDetail> findAllByPlaceOrderId(Long placeOrderId);
}
