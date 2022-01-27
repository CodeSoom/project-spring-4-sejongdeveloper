package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.errors.ObtainOrderDetailNotFoundException;
import com.codesoom.sejongdeveloper.repository.ObtainOrderDetailRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ObtainOrderDetailServiceTest {

    private static final Long OBTAIN_ORDER_DETAIL_ID = 1L;

    private static final Long INVALID_OBTAIN_ORDER_DETAIL_ID = 2L;

    private ObtainOrderDetailService obtainOrderDetailService;

    private ObtainOrderDetailRepository obtainOrderDetailRepository = mock(ObtainOrderDetailRepository.class);

    @BeforeEach
    void setUp() {
        obtainOrderDetailService = new ObtainOrderDetailService(obtainOrderDetailRepository);

        ObtainOrderDetail obtainOrderDetail = ObtainOrderDetail.builder()
                .id(OBTAIN_ORDER_DETAIL_ID)
                .build();

        given(obtainOrderDetailRepository.findById(OBTAIN_ORDER_DETAIL_ID)).willReturn(Optional.of(obtainOrderDetail));
    }

    @Test
    void createObtainOrderDetails() {
        List<ObtainOrderDetail> obtainOrderDetails = new ArrayList<>();

        obtainOrderDetailService.createObtainOrderDetails(obtainOrderDetails);

        verify(obtainOrderDetailRepository).saveAll(any(List.class));
    }

    @Test
    void updateObtainOrderDetails() {
        Item item = Item.builder().build();

        ObtainOrderDetail obtainOrderDetail = ObtainOrderDetail.builder()
                .id(OBTAIN_ORDER_DETAIL_ID)
                .item(item)
                .quantity(new BigDecimal(2_000))
                .build();

        obtainOrderDetailService.updateObtainOrderDetails(List.of(obtainOrderDetail));

        verify(obtainOrderDetailRepository).findById(eq(OBTAIN_ORDER_DETAIL_ID));
    }

    @Test
    void updateWithoutObtainOrderDetail() {
        ObtainOrderDetail obtainOrderDetail = ObtainOrderDetail.builder()
                .id(INVALID_OBTAIN_ORDER_DETAIL_ID)
                .build();

        Assertions.assertThatThrownBy(
                () -> obtainOrderDetailService.updateObtainOrderDetails(List.of(obtainOrderDetail))
        ).isInstanceOf(ObtainOrderDetailNotFoundException.class);
    }

}