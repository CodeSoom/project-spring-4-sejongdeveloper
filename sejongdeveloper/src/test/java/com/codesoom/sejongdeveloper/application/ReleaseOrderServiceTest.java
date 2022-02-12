package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderUpdateRequest;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ReleaseOrderService 클래스")
class ReleaseOrderServiceTest {

    private static final String RELEASE_ORDER_NAME = "출고명"; //출고명
    private static final LocalDate RELEASE_ORDER_DATE = LocalDate.of(2022, 2, 8);   //출고날짜
    private static final Long OBTAIN_ORDER__DETAIL_ID = 1L; //출고상세 일련번호
    private static final Long RELEASE_ORDER_ID = 1L;    //출고 일련번호

    private ReleaseOrderService releaseOrderService;
    private ReleaseOrderRepository releaseOrderRepository;
    private ReleaseOrderDetailService releaseOrderDetailService;

    @BeforeEach
    void setUp() {
        releaseOrderRepository = mock(ReleaseOrderRepository.class);
        releaseOrderDetailService = mock(ReleaseOrderDetailService.class);
        releaseOrderService = new ReleaseOrderService(releaseOrderRepository, releaseOrderDetailService);

        ReleaseOrder releaseOrder = ReleaseOrder.builder()
                .id(RELEASE_ORDER_ID)
                .name(RELEASE_ORDER_NAME)
                .date(RELEASE_ORDER_DATE)
                .build();

        given(releaseOrderRepository.save(any(ReleaseOrder.class))).willReturn(releaseOrder);

        given(releaseOrderRepository.findById(eq(RELEASE_ORDER_ID))).willReturn(Optional.of(releaseOrder));
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class saveReleaseOrder_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유효한_파라미터인_경우 {

            private ReleaseOrderSaveRequest validRequest;

            @BeforeEach
            void setUp() {
                validRequest = getParam();
            }

            private ReleaseOrderSaveRequest getParam() {
                return ReleaseOrderSaveRequest.builder()
                        .name(RELEASE_ORDER_NAME)
                        .releaseOrderDetails(List.of(getReleaseOrderDetail()))
                        .date(RELEASE_ORDER_DATE)
                        .build();
            }

            private ReleaseOrderDetailSaveRequest getReleaseOrderDetail() {
                return ReleaseOrderDetailSaveRequest.builder()
                        .obtainOrderDetailId(OBTAIN_ORDER__DETAIL_ID)
                        .quantity(new BigDecimal(1_000))
                        .build();
            }

            @Test
            @DisplayName("출고를 저장한다")
            void 출고를_저장한다() {
                Long savedId = releaseOrderService.saveReleaseOrder(validRequest);

                assertThat(savedId).isNotNull();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class updateReleaseOrder_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_출고가_존재하는_경우 {
            private ReleaseOrderUpdateRequest request;

            @BeforeEach
            void setUp() {
                request = new ReleaseOrderUpdateRequest();
                request.setName(RELEASE_ORDER_NAME + "수정");
            }

            @Test
            @DisplayName("출고를 수정한다")
            void 수주를_수정한다() {
                ReleaseOrder releaseOrder = releaseOrderService.updateReleaseOrder(RELEASE_ORDER_ID, request);

                assertThat(releaseOrder.getName()).isEqualTo(request.getName());
            }
        }
    }
}
