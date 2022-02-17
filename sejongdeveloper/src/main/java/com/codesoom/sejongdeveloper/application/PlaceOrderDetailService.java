package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.domain.PlaceOrderDetail;
import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailUpdateRequest;
import com.codesoom.sejongdeveloper.errors.ItemNotFoundException;
import com.codesoom.sejongdeveloper.errors.PlaceOrderDetailNotFoundException;
import com.codesoom.sejongdeveloper.repository.ItemRepository;
import com.codesoom.sejongdeveloper.repository.PlaceOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 발주상세에 대한 기능을 관리한다.
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlaceOrderDetailService {

    private final PlaceOrderDetailRepository placeOrderDetailRepository;
    private final ItemRepository itemRepository;

    /**
     * 주어진 발주상세 목록을 저장한다.
     *
     * @param placeOrder 주어진 발주
     * @param requests 저장할 발주상세 목록
     */
    @Transactional
    public void savePlaceOrderDetails(PlaceOrder placeOrder, List<PlaceOrderDetailSaveRequest> requests) {
        List<PlaceOrderDetail> list = getPlaceOrderDetails(placeOrder, requests);

        placeOrderDetailRepository.saveAll(list);
    }

    /**
     * 주어진 발주상세 요청객체 목록을 발주상세 엔티티 목록으로 변환하여 리턴한다.
     *
     * @param placeOrder 주어진 발주 엔티티
     * @param placeOrderDetails 주어진 발주상세 요청객체 목록
     * @return 주어진 발주상세 요청객체 목록에서 변환된 발주상세 엔티티 목록
     */
    private List<PlaceOrderDetail> getPlaceOrderDetails(PlaceOrder placeOrder,
                                                        List<PlaceOrderDetailSaveRequest> placeOrderDetails) {
        return placeOrderDetails.stream()
                .map(request -> getPlaceOrderDetail(placeOrder, request))
                .collect(Collectors.toList());
    }

    /**
     * 주어진 발주상세 요청객체에서 발주상세 엔티티를 리턴한다.
     *
     * @param placeOrder 주어진 발주 엔티티
     * @param request 주어진 발주상세 요청객체
     * @return 발주상세 엔티티
     */
    private PlaceOrderDetail getPlaceOrderDetail(PlaceOrder placeOrder, PlaceOrderDetailSaveRequest request) {
        Item item = getItem(request.getItemId());

        return new PlaceOrderDetail(request, placeOrder, item);
    }

    /**
     * 주어진 아이디의 품목을 리턴한다.
     *
     * @param id 품목 아이디
     * @return 주어진 아이디의 품목
     * @throws ItemNotFoundException 주어진 아이디의 품목을 찾지 못한 경우
     */
    private Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @Transactional
    public void update(PlaceOrder placeOrder, List<PlaceOrderDetailUpdateRequest> requests) {
        requests.forEach(request -> {
            PlaceOrderDetail placeOrderDetail = placeOrderDetailRepository.findById(request.getId())
                    .orElseThrow(() -> new PlaceOrderDetailNotFoundException(request.getId()));

            placeOrderDetail.update(request);
        });
    }
}
