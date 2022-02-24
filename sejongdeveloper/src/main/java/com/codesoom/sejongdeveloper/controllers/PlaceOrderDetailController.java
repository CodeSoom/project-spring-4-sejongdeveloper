package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/place-order-details")
public class PlaceOrderDetailController {

    @GetMapping("/place-orders/{placeOrderId}")
    public List<PlaceOrderDetailResponse> list() {
        return null;
    }
}
