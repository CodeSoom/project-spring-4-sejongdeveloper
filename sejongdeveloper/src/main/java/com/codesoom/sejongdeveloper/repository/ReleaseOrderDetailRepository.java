package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.ReleaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseOrderDetailRepository extends JpaRepository<ReleaseOrderDetail, Long>, ReleaseOrderDetailRepositoryCustom {
}
