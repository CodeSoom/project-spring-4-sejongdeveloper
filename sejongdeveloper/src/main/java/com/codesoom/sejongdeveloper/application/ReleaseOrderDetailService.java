package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.domain.ReleaseOrderDetail;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.errors.ObtainOrderDetailNotFoundException;
import com.codesoom.sejongdeveloper.repository.ObtainOrderDetailRepository;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReleaseOrderDetailService {

    private final ReleaseOrderDetailRepository releaseOrderDetailRepository;
    private final ObtainOrderDetailRepository obtainOrderDetailRepository;

    public void saveReleaseOrderDetails(ReleaseOrder releaseOrder, List<ReleaseOrderDetailSaveRequest> releaseOrderDetails) {
        List<ReleaseOrderDetail> list = releaseOrderDetails.stream()
                .map(source -> {
                    ObtainOrderDetail obtainOrderDetail = obtainOrderDetailRepository.findById(source.getObtainOrderDetailId())
                            .orElseThrow(() -> new ObtainOrderDetailNotFoundException(source.getObtainOrderDetailId()));

                    return ReleaseOrderDetail.builder()
                            .releaseOrder(releaseOrder)
                            .obtainOrderDetail(obtainOrderDetail)
                            .quantity(source.getQuantity())
                            .build();
                }).collect(Collectors.toList());

        releaseOrderDetailRepository.saveAll(list);
    }
}
