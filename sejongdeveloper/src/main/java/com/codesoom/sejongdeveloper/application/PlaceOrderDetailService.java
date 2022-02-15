package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.domain.PlaceOrderDetail;
import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.errors.ItemNotFoundException;
import com.codesoom.sejongdeveloper.repository.ItemRepository;
import com.codesoom.sejongdeveloper.repository.PlaceOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlaceOrderDetailService {

    private final PlaceOrderDetailRepository placeOrderDetailRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void savePlaceOrderDetails(PlaceOrder placeOrder, List<PlaceOrderDetailSaveRequest> requests) {
        List<PlaceOrderDetail> list = getPlaceOrderDetails(placeOrder, requests);

        placeOrderDetailRepository.saveAll(list);
    }

    private List<PlaceOrderDetail> getPlaceOrderDetails(PlaceOrder placeOrder,
                                                        List<PlaceOrderDetailSaveRequest> placeOrderDetails) {
        return placeOrderDetails.stream()
                .map(request -> getPlaceOrderDetail(placeOrder, request))
                .collect(Collectors.toList());
    }

    private PlaceOrderDetail getPlaceOrderDetail(PlaceOrder placeOrder, PlaceOrderDetailSaveRequest request) {
        Item item = getItem(request.getItemId());

        return new PlaceOrderDetail(request, placeOrder, item);
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }
}
