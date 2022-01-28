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
        obtainOrderDetails.forEach(source -> getObtainOrderDetail(source.getId())
                .update(
                        source.getItem(),
                        source.getQuantity()
                )
        );
    }

    private ObtainOrderDetail getObtainOrderDetail(Long id) {
        return obtainOrderDetailRepository.findById(id)
                .orElseThrow(() -> new ObtainOrderDetailNotFoundException(id));
    }
}
