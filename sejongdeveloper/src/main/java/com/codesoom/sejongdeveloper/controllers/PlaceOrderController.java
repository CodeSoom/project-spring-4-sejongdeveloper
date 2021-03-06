package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.PlaceOrderQueryService;
import com.codesoom.sejongdeveloper.application.PlaceOrderService;
import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderResponse;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;
import com.codesoom.sejongdeveloper.dto.PlaceOrderUpdateRequest;
import com.codesoom.sejongdeveloper.errors.PlaceOrderNotFoundException;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final PlaceOrderQueryService placeOrderQueryService;

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

    /**
     * 주어진 아이디의 발주를 리턴한다.
     *
     * @param id 발주의 아이디
     * @return 주어진 아이디의 발주
     */
    @GetMapping("{id}")
    public PlaceOrderResponse detail(@PathVariable Long id) {
        PlaceOrder placeOrder = getPlaceOrder(id);

        return PlaceOrderResponse.builder()
                .id(placeOrder.getId())
                .name(placeOrder.getName())
                .date(placeOrder.getDate())
                .build();
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

    /**
     * 주어진 아이디의 발주를 수정한다.
     *
     * @param id 발주의 아이디
     * @param request 수정할 발주
     */
    @PatchMapping("{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid PlaceOrderUpdateRequest request) {
        placeOrderService.updatePlaceOrder(id, request);
    }

    /**
     * 주어진 검색조건을 만족하는 발주목록 페이지를 리턴한다.
     *
     * @param condition 발주 검색조건
     * @return 주어진 검색조건을 만족하는 발주목록 페이지
     */
    @GetMapping
    public Page<PlaceOrderResponse> list(PlaceOrderSearchCondition condition, Pageable pageable) {
        return placeOrderQueryService.search(condition, pageable);
    }
}
