package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderResponse;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderRepository;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReleaseOrderQueryService {

    private final ReleaseOrderRepository releaseOrderRepository;

    public Page<ReleaseOrderResponse> search(ReleaseOrderSearchCondition condition, Pageable pageable) {
        QueryResults<ReleaseOrder> queryResults = releaseOrderRepository.search(condition, pageable);

        List<ReleaseOrderResponse> content = queryResults.getResults().stream()
                .map(ReleaseOrderResponse::new)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, queryResults.getTotal());
    }

}
