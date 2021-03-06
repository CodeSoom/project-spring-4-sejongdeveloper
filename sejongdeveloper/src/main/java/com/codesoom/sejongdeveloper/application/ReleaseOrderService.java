package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderUpdateRequest;
import com.codesoom.sejongdeveloper.errors.ReleaseOrderNotFoundException;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderRepository;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
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
    private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    /**
     * 출고를 저장하고 저장된 출고의 일련번호를 리턴한다.
     *
     * @param source 저장할 출고
     * @return 저장된 출고의 일련번호
     */
    @Transactional
    public Long saveReleaseOrder(ReleaseOrderSaveRequest source) {
        ReleaseOrder releaseOrder = mapper.map(source, ReleaseOrder.class);

        ReleaseOrder savedReleaseOrder = releaseOrderRepository.save(releaseOrder);
        releaseOrderDetailService.saveReleaseOrderDetails(savedReleaseOrder, source.getReleaseOrderDetails());

        return savedReleaseOrder.getId();
    }

    @Transactional
    public ReleaseOrder updateReleaseOrder(Long id, ReleaseOrderUpdateRequest request) {
        ReleaseOrder releaseOrder = getReleaseOrder(id);

        releaseOrder.update(request);

        releaseOrderDetailService.updateReleaseOrderDetails(request.getReleaseOrderDetails());

        return releaseOrder;
    }

    private ReleaseOrder getReleaseOrder(Long id) {
        return releaseOrderRepository.findById(id)
                .orElseThrow(() -> new ReleaseOrderNotFoundException(id));
    }
}
