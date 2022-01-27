package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
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
}
