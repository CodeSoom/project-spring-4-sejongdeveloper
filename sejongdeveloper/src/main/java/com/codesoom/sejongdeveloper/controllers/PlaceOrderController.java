package com.codesoom.sejongdeveloper.controllers;

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

    @PostMapping
    public Long save(@RequestBody @Valid PlaceOrderSaveRequest request) {
        return null;
    }

}
