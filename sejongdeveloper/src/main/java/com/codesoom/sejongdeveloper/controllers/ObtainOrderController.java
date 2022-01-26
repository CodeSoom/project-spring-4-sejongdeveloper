package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.ObtainOrderService;
import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.dto.ObtainOrderRequest;
import com.codesoom.sejongdeveloper.errors.ItemNotFoundException;
import com.codesoom.sejongdeveloper.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/obtain-orders")
public class ObtainOrderController {

    private final ObtainOrderService obtainOrderService;
    private final ItemRepository itemRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long create(@RequestBody ObtainOrderRequest obtainOrderRequest) {
        ObtainOrder obtainOrder = obtainOrderRequest.createObtainOrder();

        List<ObtainOrderDetail> obtainOrderDetails = obtainOrderRequest.getObtainOrderDetails().stream()
                .map(obtainOrderDetailRequest -> {
                    Item item = itemRepository.findById(obtainOrderDetailRequest.getItemId())
                            .orElseThrow(() -> new ItemNotFoundException(obtainOrderDetailRequest.getItemId()));

                    return obtainOrderDetailRequest.createObtainOrderDetail(item);
                }).collect(Collectors.toList());

        return obtainOrderService.save(obtainOrder, obtainOrderDetails);
    }
}
