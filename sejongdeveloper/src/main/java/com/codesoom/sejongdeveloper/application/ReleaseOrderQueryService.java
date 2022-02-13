package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderResponse;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderRepository;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 출고 쿼리에 대하여 관리한다.
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReleaseOrderQueryService {

    private final ReleaseOrderRepository releaseOrderRepository;

    /**
     * 주어진 검색조건과 일치하는 출고목록 페이지를 리턴한다.
     *
     * @param condition 검색조건
     * @return 주어진 검색조건과 일치하는 출고목록
     */
    public Page<ReleaseOrderResponse> search(ReleaseOrderSearchCondition condition) {
        QueryResults<ReleaseOrder> queryResults = releaseOrderRepository.search(condition);

        List<ReleaseOrderResponse> content = queryResults.getResults().stream()
                .map(ReleaseOrderResponse::new)
                .collect(Collectors.toList());

        return new PageImpl<>(content, condition.getPageable(), queryResults.getTotal());
    }

}
