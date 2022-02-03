package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.dto.ObtainOrderResponse;
import com.codesoom.sejongdeveloper.dto.ObtainOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 수주에 대한 쿼리를 관리한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtainOrderQueryService {

    private final ObtainOrderRepository obtainOrderRepository;

    /**
     * 주어진 검색 조건의 수주에 대한 목록 조회 페이지를 리턴한다.
     *
     * @param condition 검색 조건
     * @return 수주에 대한 목록 조회 페이지
     */
    public Page<ObtainOrderResponse> findAll(ObtainOrderSearchCondition condition) {
        return null;
    }

}
