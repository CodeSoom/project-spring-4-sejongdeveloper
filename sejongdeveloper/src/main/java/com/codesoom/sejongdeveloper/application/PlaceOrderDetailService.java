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

    public void savePlaceOrderDetails(PlaceOrder placeOrder, List<PlaceOrderDetailSaveRequest> placeOrderDetails) {
        List<PlaceOrderDetail> list = placeOrderDetails.stream()
                .map(request -> {
                    Item item = getItem(request.getItemId());

                    return new PlaceOrderDetail(request, placeOrder, item);
                }).collect(Collectors.toList());

        placeOrderDetailRepository.saveAll(list);
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }
}
