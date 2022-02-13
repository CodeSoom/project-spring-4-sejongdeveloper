package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSearchCondition;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

public interface ReleaseOrderRepositoryCustom {
    QueryResults<ReleaseOrder> search(ReleaseOrderSearchCondition condition, Pageable pageable);
}
