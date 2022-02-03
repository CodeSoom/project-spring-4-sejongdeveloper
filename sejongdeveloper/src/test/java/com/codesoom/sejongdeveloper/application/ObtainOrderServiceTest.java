package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.dto.ObtainOrderDetailResponse;
import com.codesoom.sejongdeveloper.dto.ObtainOrderResponse;
import com.codesoom.sejongdeveloper.dto.ObtainOrderSearchCondition;
import com.codesoom.sejongdeveloper.errors.ObtainOrderNotFoundException;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import com.querydsl.core.QueryResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ObtainOrderServiceTest {

    private static final Long OBTAIN_ORDER_ID = 1L;
    private static final String UPDATE_OBTAIN_ORDER_NAME = "수정된 수주명 이름";
    private static final Long INVALID_OBTAIN_ORDER_ID = 2L;
    private static final Long OBTAIN_ORDER_DETAIL_ID = 1L;
    private static final int OBTAIN_ORDER_DETAIL_SIZE = 1;

    private ObtainOrderService obtainOrderService;

    private ObtainOrderRepository obtainOrderRepository = mock(ObtainOrderRepository.class);

    private ObtainOrderDetailService obtainOrderDetailService = mock(ObtainOrderDetailService.class);

    @BeforeEach
    void setUp() {
        obtainOrderService = new ObtainOrderService(obtainOrderRepository, obtainOrderDetailService);

        ObtainOrder obtainOrder = ObtainOrder.builder()
                .id(OBTAIN_ORDER_ID)
                .build();

        ObtainOrderDetail obtainOrderDetail = ObtainOrderDetail.builder()
                .id(OBTAIN_ORDER_DETAIL_ID)
                .obtainOrder(obtainOrder)
                .build();


        given(obtainOrderRepository.save(any(ObtainOrder.class))).willReturn(obtainOrder);

        ObtainOrderResponse obtainOrderResponse = ObtainOrderResponse.builder()
                .id(OBTAIN_ORDER_ID)
                .build();

        ObtainOrderDetailResponse obtainOrderDetailResponse = ObtainOrderDetailResponse.builder()
                .id(OBTAIN_ORDER_DETAIL_ID)
                .obtainOrder(obtainOrderResponse)
                .build();

        given(obtainOrderDetailService.getObtainOrderDetails(OBTAIN_ORDER_ID))
                .willReturn(List.of(obtainOrderDetailResponse));

        given(obtainOrderRepository.findById(OBTAIN_ORDER_ID)).willReturn(Optional.of(obtainOrder));

        List<ObtainOrder> obtainOrders = new ArrayList<>();
        obtainOrders.add(obtainOrder);

        given(obtainOrderRepository.findAll(any(ObtainOrderSearchCondition.class)))
                .willReturn(new QueryResults<>(obtainOrders, 10L, 0L, 1L));
    }

    @DisplayName("수주를 저장한다.")
    @Test
    void createObtainOrder() {
        ObtainOrder obtainOrder = ObtainOrder.builder().build();
        List<ObtainOrderDetail> obtainOrderDetails = new ArrayList<>();

        obtainOrderService.createObtainOrder(obtainOrder, obtainOrderDetails);

        verify(obtainOrderRepository).save(any(ObtainOrder.class));
        verify(obtainOrderDetailService).createObtainOrderDetails(anyList());
    }

    @DisplayName("수주를 수정한다.")
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

        verify(obtainOrderDetailService).updateObtainOrderDetails(anyList());
    }

    @DisplayName("존재하지 않는 아이디의 수주를 수정한다.")
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

    @DisplayName("주어진 아이디의 수주를 상세조회한다.")
    @Test
    void getObtainOrder() {
        ObtainOrderResponse obtainOrder = obtainOrderService.findObtainOrder(OBTAIN_ORDER_ID);

        assertThat(obtainOrder.getId()).isEqualTo(OBTAIN_ORDER_ID);
        assertThat(obtainOrder.getObtainOrderDetails()).hasSize(OBTAIN_ORDER_DETAIL_SIZE);
    }

}
