package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.dto.ObtainOrderResponse;
import com.codesoom.sejongdeveloper.dto.ObtainOrderSearchCondition;
import com.codesoom.sejongdeveloper.errors.ObtainOrderNotFoundException;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtainOrderService {

    private final ObtainOrderRepository obtainOrderRepository;
    private final ObtainOrderDetailService obtainOrderDetailService;

    @Transactional
    public Long createObtainOrder(ObtainOrder obtainOrder, List<ObtainOrderDetail> obtainOrderDetails) {
        ObtainOrder savedObtainOrder = obtainOrderRepository.save(obtainOrder);

        obtainOrderDetailService.createObtainOrderDetails(obtainOrderDetails);

        return savedObtainOrder.getId();
    }

    @Transactional
    public Long updateObtainOrder(Long id, ObtainOrder obtainOrder, List<ObtainOrderDetail> obtainOrderDetails) {
        ObtainOrder updatedObtainOrder = getObtainOrder(id);

        updatedObtainOrder.update(
                obtainOrder.getName(),
                obtainOrder.getDate()
        );

        obtainOrderDetailService.updateObtainOrderDetails(obtainOrderDetails);

        return updatedObtainOrder.getId();
    }

    public ObtainOrderResponse findObtainOrder(Long id) {
        ObtainOrder obtainOrder = getObtainOrder(id);

        return ObtainOrderResponse.builder()
                .id(obtainOrder.getId())
                .name(obtainOrder.getName())
                .date(obtainOrder.getDate())
                .obtainOrderDetails(obtainOrderDetailService.getObtainOrderDetails(id))
                .build();
    }

    private ObtainOrder getObtainOrder(Long id) {
        return obtainOrderRepository.findById(id)
                .orElseThrow(() -> new ObtainOrderNotFoundException(id));
    }

    public Page<ObtainOrderResponse> findObtainOrders(ObtainOrderSearchCondition condition) {
        QueryResults<ObtainOrder> queryResults = obtainOrderRepository.findAll(condition);

        List<ObtainOrderResponse> content = queryResults.getResults().stream()
                .map(obtainOrder -> ObtainOrderResponse.builder().build())
                .collect(Collectors.toList());

        return new PageImpl<>(content, condition.getPageable(), queryResults.getTotal());
    }
}
