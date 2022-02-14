package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlaceOrderService {


    public Long savePlaceOrder(PlaceOrderSaveRequest request) {
        return null;
    }
}
