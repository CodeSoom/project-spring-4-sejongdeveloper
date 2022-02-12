package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.ReleaseOrderDetail;

import java.util.List;

public interface ReleaseOrderDetailRepositoryCustom {
    List<ReleaseOrderDetail> findAllByReleaseOrderId(Long releaseOrderId);
}
