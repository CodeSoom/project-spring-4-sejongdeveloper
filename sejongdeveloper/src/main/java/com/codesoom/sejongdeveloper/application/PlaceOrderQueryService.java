package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.dto.PlaceOrderResponse;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlaceOrderQueryService {

    public Page<PlaceOrderResponse> search(PlaceOrderSearchCondition condition) {
        return null;
    }

}
