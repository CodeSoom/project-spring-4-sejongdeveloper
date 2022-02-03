package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.dto.ObtainOrderResponse;
import com.codesoom.sejongdeveloper.dto.ObtainOrderSearchCondition;
import com.codesoom.sejongdeveloper.errors.ObtainOrderNotFoundException;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<ObtainOrderResponse> findAll(ObtainOrderSearchCondition condition) {
        return null;
    }
}
