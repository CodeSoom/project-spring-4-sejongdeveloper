package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.dto.ObtainOrderSearchCondition;
import com.querydsl.core.QueryResults;

public interface ObtainOrderRepositoryCustom {
    QueryResults<ObtainOrder> findAll(ObtainOrderSearchCondition condition);
}
