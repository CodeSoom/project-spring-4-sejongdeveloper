package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;

import java.util.List;

public interface ObtainOrderDetailRepositoryCustom {
    List<ObtainOrderDetail> findAllByObtainOrderId(Long obtainOrderId);
}
