package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.repository.ObtainOrderDetailRepository;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ReleaseOrderDetailService 클래스")
class ReleaseOrderDetailServiceTest {

    private static final Long VALID_OBTAIN_ORDER_DETAIL_ID = 1L;    //유효한 수주상세 일련번호
    private static final BigDecimal ITEM_QUANTITY = new BigDecimal(1_000);   //품목수량

    private ReleaseOrderDetailService releaseOrderDetailService;
    private ReleaseOrder releaseOrder;
    private ReleaseOrderDetailRepository releaseOrderDetailRepository;
    private ObtainOrderDetailRepository obtainOrderDetailRepository;

    @BeforeEach
    void setUp() {
        releaseOrderDetailRepository = mock(ReleaseOrderDetailRepository.class);
        obtainOrderDetailRepository = mock(ObtainOrderDetailRepository.class);
        releaseOrderDetailService = new ReleaseOrderDetailService(releaseOrderDetailRepository, obtainOrderDetailRepository);

        releaseOrder = ReleaseOrder.builder().build();

        Item item = Item.builder()
                .quantity(ITEM_QUANTITY)
                .build();

        ObtainOrderDetail obtainOrderDetail = ObtainOrderDetail.builder()
                .id(VALID_OBTAIN_ORDER_DETAIL_ID)
                .item(item)
                .build();

        given(obtainOrderDetailRepository.findById(VALID_OBTAIN_ORDER_DETAIL_ID)).willReturn(Optional.of(obtainOrderDetail));
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class saveReleaseOrderDetails_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 품목수량이_출고수량_보다_같거나_많은_경우 {
            private List<ReleaseOrderDetailSaveRequest> validParam;

            @BeforeEach
            void setUp() {
                ReleaseOrderDetailSaveRequest detail1 = getDetail(ITEM_QUANTITY);
                ReleaseOrderDetailSaveRequest detail2 = getDetail(ITEM_QUANTITY.subtract(new BigDecimal(1)));

                validParam = List.of(detail1, detail2);
            }

            private ReleaseOrderDetailSaveRequest getDetail(BigDecimal quantity) {
                return ReleaseOrderDetailSaveRequest.builder()
                        .obtainOrderDetailId(VALID_OBTAIN_ORDER_DETAIL_ID)
                        .quantity(quantity)
                        .build();
            }

            @Test
            @DisplayName("출고상세를 저장한다")
            void 출고상세를_저장한다() {
                releaseOrderDetailService.saveReleaseOrderDetails(releaseOrder, validParam);
            }
        }
    }
}
