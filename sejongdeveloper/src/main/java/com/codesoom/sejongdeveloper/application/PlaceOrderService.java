package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderResponse;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import com.codesoom.sejongdeveloper.dto.PlaceOrderUpdateRequest;
import com.codesoom.sejongdeveloper.errors.PlaceOrderNotFoundException;
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
    @Transactional
    public Long savePlaceOrder(PlaceOrderSaveRequest request) {
        PlaceOrder placeOrder = new PlaceOrder(request);

        PlaceOrder savedPlaceOrder = placeOrderRepository.save(placeOrder);

        placeOrderDetailService.savePlaceOrderDetails(savedPlaceOrder, request.getPlaceOrderDetails());

        return savedPlaceOrder.getId();
    }

    /**
     * 주어진 아이디의 발주를 수정한다.
     *
     * @param id 발주의 아이디
     * @param request 수정할 발주
     */
    @Transactional
    public void updatePlaceOrder(Long id, PlaceOrderUpdateRequest request) {
        PlaceOrder placeOrder = getPlaceOrder(id);

        placeOrder.update(request);
    }

    /**
     * 주어진 아이디의 발주를 리턴한다.
     *
     * @param id 발주의 아이디
     * @return 주어진 아이디의 발주
     * @throws PlaceOrderNotFoundException 주어진 아이디의 발주를 찾지 못한 경우
     */
    private PlaceOrder getPlaceOrder(Long id) {
        return placeOrderRepository.findById(id)
                .orElseThrow(() -> new PlaceOrderNotFoundException(id));
    }
}
