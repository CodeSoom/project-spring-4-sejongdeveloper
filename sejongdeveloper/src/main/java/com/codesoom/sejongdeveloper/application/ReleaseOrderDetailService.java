package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.domain.ReleaseOrderDetail;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailUpdateRequest;
import com.codesoom.sejongdeveloper.errors.ItemNotEnoughException;
import com.codesoom.sejongdeveloper.errors.ObtainOrderDetailNotFoundException;
import com.codesoom.sejongdeveloper.errors.ReleaseOrderDetailNotFoundException;
import com.codesoom.sejongdeveloper.errors.ReleaseOrderDetailOverSize;
import com.codesoom.sejongdeveloper.repository.ObtainOrderDetailRepository;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 출고상세를 관리한다.
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReleaseOrderDetailService {

    private final ReleaseOrderDetailRepository releaseOrderDetailRepository;
    private final ObtainOrderDetailRepository obtainOrderDetailRepository;

    /**
     * 주어진 출고상세 목록을 저장한다.
     *
     * @param releaseOrder 주어진 출고상세의 출고
     * @param releaseOrderDetails 저장할 출고상세
     */
    @Transactional
    public void saveReleaseOrderDetails(ReleaseOrder releaseOrder,
                                        List<ReleaseOrderDetailSaveRequest> releaseOrderDetails) {

        if (releaseOrderDetails.size() > 1_000) {
            throw new ReleaseOrderDetailOverSize(1_000, releaseOrderDetails.size());
        }

        List<ReleaseOrderDetail> list = releaseOrderDetails.stream()
                .map(source -> getReleaseOrderDetail(releaseOrder, source))
                .collect(Collectors.toList());

        releaseOrderDetailRepository.saveAll(list);
    }

    /**
     * 출고상세 엔티티를 리턴한다.
     *
     * @param releaseOrder 출고상세의 출고
     * @param source 저장할 출고상세
     * @return 저장할 출고상세 엔티티
     * @throws ItemNotEnoughException 출고수량이 품목수량보다 많은 경우
     */
    private ReleaseOrderDetail getReleaseOrderDetail(ReleaseOrder releaseOrder, ReleaseOrderDetailSaveRequest source) {
        return ReleaseOrderDetail.builder()
                .releaseOrder(releaseOrder)
                .obtainOrderDetail(getObtainOrderDetail(source.getObtainOrderDetailId()))
                .quantity(source.getQuantity())
                .build();
    }

    /**
     * 주어진 id의 수주상세 엔티티를 리턴한다.
     *
     * @param id 수주상세 일련번호
     * @return 주어진 id의 수주상세 엔티티
     */
    private ObtainOrderDetail getObtainOrderDetail(Long id) {
        return obtainOrderDetailRepository.findById(id)
                .orElseThrow(() -> new ObtainOrderDetailNotFoundException(id));
    }

    /**
     * 주어진 출고상세 목록을 수정하고 수정된 출고상세 목록을 리턴한다.
     *
     * @param list 수정할 출고상세 목록
     * @return 수정된 출고상세 목록
     */
    @Transactional
    public List<ReleaseOrderDetail> updateReleaseOrderDetails(List<ReleaseOrderDetailUpdateRequest> list) {
        return list.stream()
                .map(request -> {
                    ReleaseOrderDetail entity = getReleaseOrderDetail(request.getId());

                    entity.update(request);

                    return entity;
                }).collect(Collectors.toList());
    }

    /**
     * 주어진 id의 출고상세를 리턴한다.
     *
     * @param id 출고상세의 id
     * @return 주어진 id의 출고상세
     * @throws ReleaseOrderDetailNotFoundException 주어진 id의 출고상세를 찾지 못한 경우
     */
    private ReleaseOrderDetail getReleaseOrderDetail(Long id) {
        return releaseOrderDetailRepository.findById(id)
                .orElseThrow(() -> new ReleaseOrderDetailNotFoundException(id));
    }
}
