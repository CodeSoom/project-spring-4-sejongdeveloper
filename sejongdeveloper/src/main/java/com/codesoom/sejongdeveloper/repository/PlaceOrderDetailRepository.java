package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.PlaceOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceOrderDetailRepository extends JpaRepository<PlaceOrderDetail, Long>, PlaceOrderDetailRepositoryCustom {
}
