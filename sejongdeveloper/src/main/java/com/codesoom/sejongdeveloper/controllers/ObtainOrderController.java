package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.ObtainOrderQueryService;
import com.codesoom.sejongdeveloper.application.ObtainOrderService;
import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.dto.ObtainOrderDetailRequest;
import com.codesoom.sejongdeveloper.dto.ObtainOrderRequest;
import com.codesoom.sejongdeveloper.dto.ObtainOrderResponse;
import com.codesoom.sejongdeveloper.dto.ObtainOrderSearchCondition;
import com.codesoom.sejongdeveloper.errors.ItemNotFoundException;
import com.codesoom.sejongdeveloper.errors.ObtainOrderNotFoundException;
import com.codesoom.sejongdeveloper.repository.ItemRepository;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/obtain-orders")
public class ObtainOrderController {

    private final ObtainOrderService obtainOrderService;
    private final ItemRepository itemRepository;
    private final ObtainOrderQueryService obtainOrderQueryService;
    private final ObtainOrderRepository obtainOrderRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long create(@RequestBody @Valid ObtainOrderRequest obtainOrderRequest) {
        ObtainOrder obtainOrder = obtainOrderRequest.createObtainOrder();

        List<ObtainOrderDetail> obtainOrderDetails = getObtainOrderDetails(obtainOrder, obtainOrderRequest);

        return obtainOrderService.createObtainOrder(obtainOrder, obtainOrderDetails);
    }

    private List<ObtainOrderDetail> getObtainOrderDetails(ObtainOrder obtainOrder,
                                                          ObtainOrderRequest obtainOrderRequest) {

        return obtainOrderRequest.getObtainOrderDetails().stream()
                .map(obtainOrderDetailRequest -> getObtainOrderDetail(obtainOrder, obtainOrderDetailRequest))
                .collect(Collectors.toList());
    }

    @PatchMapping("{id}")
    public Long update(@PathVariable Long id, @RequestBody @Valid ObtainOrderRequest obtainOrderRequest) {
        ObtainOrder obtainOrder = getObtainOrder(id);

        List<ObtainOrderDetail> saveObtainOrderDetails = saveObtainOrderDetails(obtainOrderRequest, obtainOrder);

        List<ObtainOrderDetail> updateObtainOrderDetails = updateObtainOrderDetails(obtainOrderRequest, obtainOrder);

        return obtainOrderService.updateObtainOrder(id, obtainOrder, saveObtainOrderDetails, updateObtainOrderDetails);
    }

    private ObtainOrder getObtainOrder(Long id) {
        return obtainOrderRepository.findById(id)
                .orElseThrow(() -> new ObtainOrderNotFoundException(id));
    }

    private List<ObtainOrderDetail> saveObtainOrderDetails(ObtainOrderRequest obtainOrderRequest, ObtainOrder obtainOrder) {
         return obtainOrderRequest.getObtainOrderDetails().stream()
                .filter(source -> source.getId() == null)
                .map(source -> getObtainOrderDetail(obtainOrder, source))
                .collect(Collectors.toList());
    }

    private List<ObtainOrderDetail> updateObtainOrderDetails(ObtainOrderRequest obtainOrderRequest, ObtainOrder obtainOrder) {
        return obtainOrderRequest.getObtainOrderDetails().stream()
                .filter(source -> source.getId() != null)
                .map(source -> getObtainOrderDetail(obtainOrder, source))
                .collect(Collectors.toList());
    }

    private ObtainOrderDetail getObtainOrderDetail(ObtainOrder obtainOrder, ObtainOrderDetailRequest source) {
        Item item = itemRepository.findById(source.getItemId())
                .orElseThrow(() -> new ItemNotFoundException(source.getItemId()));

        return source.createObtainOrderDetail(obtainOrder, item);
    }

    @GetMapping("{id}")
    public ObtainOrderResponse detail(@PathVariable Long id) {
        return obtainOrderService.findObtainOrder(id);
    }

    @GetMapping
    public Page<ObtainOrderResponse> list(ObtainOrderSearchCondition condition, Pageable pageable) {
        return obtainOrderQueryService.findObtainOrders(condition, pageable);
    }
}
