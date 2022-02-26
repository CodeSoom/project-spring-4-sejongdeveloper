package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.dto.ItemResponse;
import com.codesoom.sejongdeveloper.dto.ObtainOrderDetailResponse;
import com.codesoom.sejongdeveloper.errors.ObtainOrderDetailNotFoundException;
import com.codesoom.sejongdeveloper.repository.ObtainOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtainOrderDetailService {

    private final ObtainOrderDetailRepository obtainOrderDetailRepository;

    @Transactional
    public void createObtainOrderDetails(List<ObtainOrderDetail> obtainOrderDetails) {
        if (obtainOrderDetails == null) {
            return;
        }

        obtainOrderDetailRepository.saveAll(obtainOrderDetails);
    }

    @Transactional
    public void updateObtainOrderDetails(List<ObtainOrderDetail> obtainOrderDetails) {
        if (obtainOrderDetails == null) {
            return;
        }

        obtainOrderDetails.forEach(source -> getObtainOrderDetail(source.getId())
                .update(
                        source.getItem(),
                        source.getQuantity(),
                        source.getUseYn()
                )
        );
    }

    private ObtainOrderDetail getObtainOrderDetail(Long id) {
        return obtainOrderDetailRepository.findById(id)
                .orElseThrow(() -> new ObtainOrderDetailNotFoundException(id));
    }

    public List<ObtainOrderDetailResponse> getObtainOrderDetails(Long obtainOrderId) {
        return obtainOrderDetailRepository.findAllByObtainOrderId(obtainOrderId).stream()
                .map(source -> ObtainOrderDetailResponse.builder()
                        .id(source.getId())
                        .item(getItemResponse(source.getItem()))
                        .quantity(source.getQuantity())
                        .useYn(source.getUseYn())
                        .build())
                .collect(Collectors.toList());
    }

    private ItemResponse getItemResponse(Item source) {
        return ItemResponse.builder()
                .id(source.getId())
                .code(source.getCode())
                .name(source.getName())
                .quantity(source.getQuantity())
                .build();
    }
}
