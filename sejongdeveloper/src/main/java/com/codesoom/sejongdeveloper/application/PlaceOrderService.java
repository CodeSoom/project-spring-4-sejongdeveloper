package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 발주에 관하여 관리한다.
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlaceOrderService {

    private final PlaceOrderRepository placeOrderRepository;
    private final PlaceOrderDetailService placeOrderDetailService;

    /**
     * 주어진 발주를 저장하고 저장된 발주 아이디 번호를 리턴한다.
     *
     * @param request 저장할 발주
     * @return 저장된 발주
     */
    public Long savePlaceOrder(PlaceOrderSaveRequest request) {
        PlaceOrder placeOrder = new PlaceOrder(request);

        PlaceOrder savedPlaceOrder = placeOrderRepository.save(placeOrder);

        placeOrderDetailService.savePlaceOrderDetails(savedPlaceOrder, request.getPlaceOrderDetails());

        return savedPlaceOrder.getId();
    }

}
