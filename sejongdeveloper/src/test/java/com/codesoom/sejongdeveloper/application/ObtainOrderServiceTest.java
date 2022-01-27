package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ObtainOrderServiceTest {

    private static final Long OBTAIN_ORDER_ID = 1L;

    private ObtainOrderService obtainOrderService;

    private ObtainOrderRepository obtainOrderRepository = mock(ObtainOrderRepository.class);

    private ObtainOrderDetailService obtainOrderDetailService = mock(ObtainOrderDetailService.class);

    @BeforeEach
    void setUp() {
        obtainOrderService = new ObtainOrderService(obtainOrderRepository, obtainOrderDetailService);

        ObtainOrder obtainOrder = ObtainOrder.builder()
                .id(OBTAIN_ORDER_ID)
                .build();

        given(obtainOrderRepository.save(any(ObtainOrder.class))).willReturn(obtainOrder);
    }

    @Test
    void createObtainOrder() {
        ObtainOrder obtainOrder = ObtainOrder.builder().build();
        List<ObtainOrderDetail> obtainOrderDetails = new ArrayList<>();

        obtainOrderService.createObtainOrder(obtainOrder, obtainOrderDetails);

        verify(obtainOrderRepository).save(any(ObtainOrder.class));
        verify(obtainOrderDetailService).createObtainOrderDetails(any(List.class));
    }

}