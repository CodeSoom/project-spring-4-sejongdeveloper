package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.errors.ObtainOrderDetailNotFoundException;
import com.codesoom.sejongdeveloper.repository.ObtainOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtainOrderDetailService {

    private final ObtainOrderDetailRepository obtainOrderDetailRepository;

    @Transactional
    public void createObtainOrderDetails(List<ObtainOrderDetail> obtainOrderDetails) {
        obtainOrderDetailRepository.saveAll(obtainOrderDetails);
    }

    @Transactional
    public void updateObtainOrderDetails(List<ObtainOrderDetail> obtainOrderDetails) {
        obtainOrderDetails.stream().forEach(obtainOrderDetail -> {
            ObtainOrderDetail updatedObtainOrderDetail = obtainOrderDetailRepository.findById(obtainOrderDetail.getId())
                    .orElseThrow(() -> new ObtainOrderDetailNotFoundException(obtainOrderDetail.getId()));

            updatedObtainOrderDetail.update(
                    obtainOrderDetail.getItem(),
                    obtainOrderDetail.getQuantity()
            );
        });
    }
}
