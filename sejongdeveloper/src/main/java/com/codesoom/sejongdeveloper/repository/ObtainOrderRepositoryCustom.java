package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.dto.ObtainOrderSearchCondition;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

public interface ObtainOrderRepositoryCustom {
    QueryResults<ObtainOrder> findAll(ObtainOrderSearchCondition condition, Pageable pageable);
}
