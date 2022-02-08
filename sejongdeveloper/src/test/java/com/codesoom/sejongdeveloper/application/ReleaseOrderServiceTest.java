package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ReleaseOrderService 클래스")
class ReleaseOrderServiceTest {

    private static final String RELEASE_ORDER_NAME = "출고명"; //출고명
    private static final LocalDate RELEASE_ORDER_DATE = LocalDate.of(2022, 2, 8);   //출고날짜
    private static final Long OBTAIN_ORDER__DETAIL_ID = 1L; //출고상세 일련번호

    private ReleaseOrderService releaseOrderService;

    @BeforeEach
    void setUp() {
        releaseOrderService = new ReleaseOrderService();
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
}
