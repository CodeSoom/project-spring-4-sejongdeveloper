package com.codesoom.sejongdeveloper.repository;

import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceOrderRepository extends JpaRepository<PlaceOrder, Long>, PlaceOrderRepositoryCustom {
}
