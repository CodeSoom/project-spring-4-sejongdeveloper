package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.errors.ObtainOrderDetailNotFoundException;
import com.codesoom.sejongdeveloper.repository.ObtainOrderDetailRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
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
    @DisplayName("수주상세를 저장한다.")
    void createObtainOrderDetails() {
        List<ObtainOrderDetail> obtainOrderDetails = new ArrayList<>();

        obtainOrderDetailService.createObtainOrderDetails(obtainOrderDetails);

        verify(obtainOrderDetailRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("수주상세를 수정한다.")
    void updateObtainOrderDetails() {
        Item item = Item.builder().build();

        ObtainOrderDetail obtainOrderDetail = ObtainOrderDetail.builder()
                .id(OBTAIN_ORDER_DETAIL_ID)
                .item(item)
                .quantity(2_000.0)
                .build();

        obtainOrderDetailService.updateObtainOrderDetails(List.of(obtainOrderDetail));

        verify(obtainOrderDetailRepository).findById(eq(OBTAIN_ORDER_DETAIL_ID));
    }

    @Test
    @DisplayName("주어진 아이디의 수주상세를 찾지 못한 경우 예외를 던진다")
    void updateWithoutObtainOrderDetail() {
        ObtainOrderDetail obtainOrderDetail = ObtainOrderDetail.builder()
                .id(INVALID_OBTAIN_ORDER_DETAIL_ID)
                .build();

        Assertions.assertThatThrownBy(
                () -> obtainOrderDetailService.updateObtainOrderDetails(List.of(obtainOrderDetail))
        ).isInstanceOf(ObtainOrderDetailNotFoundException.class);
    }
}
