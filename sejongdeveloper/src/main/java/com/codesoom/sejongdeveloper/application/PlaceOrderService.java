package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlaceOrderService {


    private final PlaceOrderRepository placeOrderRepository;

    public Long savePlaceOrder(PlaceOrderSaveRequest request) {
        PlaceOrder placeOrder = new PlaceOrder(request);

        PlaceOrder savedPlaceOrder = placeOrderRepository.save(placeOrder);

        return savedPlaceOrder.getId();
    }
}
