package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderResponse;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 발주의 쿼리관련에 대하여 관리한다.
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PlaceOrderQueryService {

    private final PlaceOrderRepository placeOrderRepository;

    /**
     * 주어진 검색조건의 발주목록 페이지를 리턴한다.
     *
     * @param condition 발주 검색조건
     * @param pageable 페이지
     * @return 주어진 검색조건의 발주목록 페이지
     */
    public Page<PlaceOrderResponse> search(PlaceOrderSearchCondition condition, Pageable pageable) {
        QueryResults<PlaceOrder> queryResults = placeOrderRepository.findAll(condition, pageable);

        List<PlaceOrderResponse> content = queryResults.getResults().stream()
                .map(entity -> PlaceOrderResponse.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .date(entity.getDate())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, queryResults.getTotal());
    }

}
