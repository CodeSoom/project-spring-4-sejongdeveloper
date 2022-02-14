package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseOrderRepository extends JpaRepository<ReleaseOrder, Long>, ReleaseOrderRepositoryCustom {
}
