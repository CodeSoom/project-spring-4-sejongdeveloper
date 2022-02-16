package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.PlaceOrderService;
import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderResponse;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import com.codesoom.sejongdeveloper.errors.PlaceOrderNotFoundException;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 발주에 대한 요청을 관리한다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/place-orders")
public class PlaceOrderController {

    private final PlaceOrderService placeOrderService;
    private final PlaceOrderRepository placeOrderRepository;

    /**
     * 주어진 발주를 저장하고 저장된 발주 아이디 번호를 리턴한다.
     *
     * @param request 저장할 발주
     * @return 저장된 발주
     */
    @PostMapping
    public Long save(@RequestBody @Valid PlaceOrderSaveRequest request) {
        return placeOrderService.savePlaceOrder(request);
    }

    @GetMapping("{id}")
    public PlaceOrderResponse detail(@PathVariable Long id) {
        PlaceOrder placeOrder = placeOrderRepository.findById(id)
                .orElseThrow(() -> new PlaceOrderNotFoundException(id));

        return PlaceOrderResponse.builder()
                .id(placeOrder.getId())
                .name(placeOrder.getName())
                .date(placeOrder.getDate())
                .build();
    }
}
