package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.domain.PlaceOrderDetail;
import com.codesoom.sejongdeveloper.dto.ItemResponse;
import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailResponse;
import com.codesoom.sejongdeveloper.repository.PlaceOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/place-order-details")
public class PlaceOrderDetailController {

    private final PlaceOrderDetailRepository placeOrderDetailRepository;

    @GetMapping("/place-orders/{placeOrderId}")
    public List<PlaceOrderDetailResponse> list(@PathVariable Long placeOrderId) {
        return placeOrderDetailRepository.findAllByPlaceOrderId(placeOrderId).stream()
                .map(entity -> {
                    ItemResponse item = ItemResponse.builder()
                            .id(entity.getItem().getId())
                            .name(entity.getItem().getName())
                            .build();

                    return PlaceOrderDetailResponse.builder()
                            .id(entity.getId())
                            .item(item)
                            .quantity(entity.getQuantity())
                            .build();
                }).collect(Collectors.toList());
    }
}
