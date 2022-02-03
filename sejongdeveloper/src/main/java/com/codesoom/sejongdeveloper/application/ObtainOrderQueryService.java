package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.dto.ObtainOrderResponse;
import com.codesoom.sejongdeveloper.dto.ObtainOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public Page<ObtainOrderResponse> findObtainOrders(ObtainOrderSearchCondition condition) {
        QueryResults<ObtainOrder> queryResults = obtainOrderRepository.findAll(condition);

        List<ObtainOrderResponse> content = queryResults.getResults().stream()
                .map(source -> ObtainOrderResponse.builder()
                        .id(source.getId())
                        .name(source.getName())
                        .date(source.getDate())
                        .build()
                ).collect(Collectors.toList());

        return new PageImpl<>(content, condition.getPageable(), queryResults.getTotal());
    }

}
