package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

class ObtainOrderServiceTest {

    private ObtainOrderService obtainOrderService;
    private ObtainOrderRepository obtainOrderRepository = mock(ObtainOrderRepository.class);

    @BeforeEach
    void setUp() {
        obtainOrderService = new ObtainOrderService(obtainOrderRepository);
    }

    @Test
    void createObtainOrder() {
        ObtainOrder obtainOrder = ObtainOrder.builder().build();
        List<ObtainOrderDetail> obtainOrderDetails = new ArrayList<>();

        obtainOrderService.createObtainOrder(obtainOrder, obtainOrderDetails);
    }

}