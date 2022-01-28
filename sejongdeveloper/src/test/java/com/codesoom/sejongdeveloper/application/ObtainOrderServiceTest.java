package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.errors.ObtainOrderNotFoundException;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ObtainOrderServiceTest {

    private static final Long OBTAIN_ORDER_ID = 1L;
    private static final String UPDATE_OBTAIN_ORDER_NAME = "수정된 수주명 이름";
    private static final Long INVALID_OBTAIN_ORDER_ID = 2L;

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

        given(obtainOrderRepository.findById(OBTAIN_ORDER_ID)).willReturn(Optional.of(obtainOrder));
    }

    @Test
    void createObtainOrder() {
        ObtainOrder obtainOrder = ObtainOrder.builder().build();
        List<ObtainOrderDetail> obtainOrderDetails = new ArrayList<>();

        obtainOrderService.createObtainOrder(obtainOrder, obtainOrderDetails);

        verify(obtainOrderRepository).save(any(ObtainOrder.class));
        verify(obtainOrderDetailService).createObtainOrderDetails(any(List.class));
    }

    @Test
    void updateObtainOrder() {
        Item item = Item.builder().build();

        ObtainOrder obtainOrder = ObtainOrder.builder()
                .name(UPDATE_OBTAIN_ORDER_NAME)
                .build();

        ObtainOrderDetail obtainOrderDetail = ObtainOrderDetail.builder()
                .item(item)
                .build();

        obtainOrderService.updateObtainOrder(OBTAIN_ORDER_ID, obtainOrder, List.of(obtainOrderDetail));

        verify(obtainOrderDetailService).updateObtainOrderDetails(any(List.class));
    }

    @Test
    void updateWithoutObtainOrder() {
        ObtainOrder obtainOrder = ObtainOrder.builder().build();
        ObtainOrderDetail obtainOrderDetail = ObtainOrderDetail.builder().build();

        assertThatThrownBy(() -> obtainOrderService.updateObtainOrder(
                INVALID_OBTAIN_ORDER_ID,
                obtainOrder,
                List.of(obtainOrderDetail))
        ).isInstanceOf(ObtainOrderNotFoundException.class);
    }

}