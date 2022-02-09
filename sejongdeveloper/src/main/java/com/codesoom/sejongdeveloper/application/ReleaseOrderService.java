package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSaveRequest;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 출고를 관리한다.
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReleaseOrderService {

    private final ReleaseOrderRepository releaseOrderRepository;
    private final ReleaseOrderDetailService releaseOrderDetailService;

    /**
     * 출고를 저장하고 저장된 출고의 일련번호를 리턴한다.
     *
     * @param source 저장할 출고
     * @return 저장된 출고의 일련번호
     */
    @Transactional
    public Long saveReleaseOrder(ReleaseOrderSaveRequest source) {
        ReleaseOrder releaseOrder = ReleaseOrder.builder()
                .name(source.getName())
                .date(source.getDate())
                .build();

        ReleaseOrder savedReleaseOrder = releaseOrderRepository.save(releaseOrder);
        releaseOrderDetailService.saveReleaseOrderDetails(savedReleaseOrder, source.getReleaseOrderDetails());

        return savedReleaseOrder.getId();
    }
}
