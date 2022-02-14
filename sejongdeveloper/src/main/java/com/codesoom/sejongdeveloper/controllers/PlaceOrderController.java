package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.PlaceOrderService;
import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderResponse;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/place-orders")
public class PlaceOrderController {

    private final PlaceOrderService placeOrderService;

    @PostMapping
    public PlaceOrderResponse save(@RequestBody @Valid PlaceOrderSaveRequest request) {
        PlaceOrder placeOrder = placeOrderService.savePlaceOrder(request);

        return new PlaceOrderResponse(placeOrder);
    }

}
