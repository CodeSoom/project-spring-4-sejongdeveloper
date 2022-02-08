package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSaveRequest;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReleaseOrderService {

    private final ReleaseOrderRepository releaseOrderRepository;
    private final ReleaseOrderDetailService releaseOrderDetailService;

    @Transactional
    public Long saveReleaseOrder(ReleaseOrderSaveRequest source) {
        ReleaseOrder releaseOrder = ReleaseOrder.builder()
                .name(source.getName())
                .date(source.getDate())
                .build();

        ReleaseOrder savedReleaseOrder = releaseOrderRepository.save(releaseOrder);
        releaseOrderDetailService.saveReleaseOrderDetails(source.getReleaseOrderDetails());

        return savedReleaseOrder.getId();
    }
}
