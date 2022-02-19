package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.domain.ReleaseOrderDetail;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailUpdateRequest;
import com.codesoom.sejongdeveloper.errors.ItemNotEnoughException;
import com.codesoom.sejongdeveloper.errors.ReleaseOrderDetailNotFoundException;
import com.codesoom.sejongdeveloper.errors.ReleaseOrderDetailOverSize;
import com.codesoom.sejongdeveloper.repository.ObtainOrderDetailRepository;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ReleaseOrderDetailService 클래스")
class ReleaseOrderDetailServiceTest {

    private static final Long OBTAIN_ORDER_DETAIL_ID = 1L;    //수주상세 일련번호
    private static final Double ITEM_QUANTITY = 1_000.0;   //품목수량
    private static final Long VALID_RELEASE_ORDER_DETAIL_ID = 1L;
    private static final Long INVALID_RELEASE_ORDER_DETAIL_ID = 2L;
    private static final Double QUANTITY = 1_000.0;

    private ReleaseOrderDetailService releaseOrderDetailService;
    private ReleaseOrder releaseOrder;
    private ReleaseOrderDetailRepository releaseOrderDetailRepository;
    private ObtainOrderDetailRepository obtainOrderDetailRepository;

    @BeforeEach
    void setUp() {
        releaseOrderDetailRepository = mock(ReleaseOrderDetailRepository.class);
        obtainOrderDetailRepository = mock(ObtainOrderDetailRepository.class);
        releaseOrderDetailService = new ReleaseOrderDetailService(
                releaseOrderDetailRepository,
                obtainOrderDetailRepository
        );

        releaseOrder = ReleaseOrder.builder().build();

        Item item = Item.builder()
                .quantity(ITEM_QUANTITY)
                .build();

        ObtainOrderDetail obtainOrderDetail = ObtainOrderDetail.builder()
                .id(OBTAIN_ORDER_DETAIL_ID)
                .item(item)
                .build();

        given(obtainOrderDetailRepository.findById(OBTAIN_ORDER_DETAIL_ID))
                .willReturn(Optional.of(obtainOrderDetail));

        ReleaseOrderDetail releaseOrderDetail = ReleaseOrderDetail.builder()
                .id(VALID_RELEASE_ORDER_DETAIL_ID)
                .releaseOrder(releaseOrder)
                .obtainOrderDetail(obtainOrderDetail)
                .quantity(QUANTITY)
                .build();

        given(releaseOrderDetailRepository.findById(VALID_RELEASE_ORDER_DETAIL_ID))
                .willReturn(Optional.of(releaseOrderDetail));
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
                ReleaseOrderDetailSaveRequest detail2 = getDetail(ITEM_QUANTITY - 1);

                validParam = List.of(detail1, detail2);
            }

            @Test
            @DisplayName("출고상세를 저장한다")
            void 출고상세를_저장한다() {
                releaseOrderDetailService.saveReleaseOrderDetails(releaseOrder, validParam);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 품목수량이_출고수량_보다_적은_경우 {
            private List<ReleaseOrderDetailSaveRequest> invalidParam;

            @BeforeEach
            void setUp() {
                ReleaseOrderDetailSaveRequest detail = getDetail(ITEM_QUANTITY + 1);

                invalidParam = List.of(detail);
            }

            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> releaseOrderDetailService.saveReleaseOrderDetails(releaseOrder, invalidParam))
                        .isInstanceOf(ItemNotEnoughException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 출고상세_개수가_최고개수를_초과한_경우 {

            private static final int OVER_SIZE = 1_000 + 1;

            private List<ReleaseOrderDetailSaveRequest> invalidParam;

            @BeforeEach
            void setUp() {
                invalidParam = new ArrayList<>();

                for (int i=0; i<OVER_SIZE; i++) {
                    invalidParam.add(getDetail((double) i));
                }
            }

            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> releaseOrderDetailService.saveReleaseOrderDetails(releaseOrder, invalidParam))
                        .isInstanceOf(ReleaseOrderDetailOverSize.class);
            }
        }

        private ReleaseOrderDetailSaveRequest getDetail(Double quantity) {
            return ReleaseOrderDetailSaveRequest.builder()
                    .obtainOrderDetailId(OBTAIN_ORDER_DETAIL_ID)
                    .quantity(quantity)
                    .build();
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class updateReleaseOrderDetails_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_출고상세가_있는_경우 {
            private List<ReleaseOrderDetailUpdateRequest> list;
            private final Double UPDATE_QUANTITY = 900.0;

            @BeforeEach
            void setUp() {
                ReleaseOrderDetailUpdateRequest request = new ReleaseOrderDetailUpdateRequest();
                request.setId(VALID_RELEASE_ORDER_DETAIL_ID);
                request.setQuantity(UPDATE_QUANTITY);

                list = List.of(request);
            }

            @Test
            @DisplayName("출고상세를 수정한다")
            void 출고상세를_수정한다() {
                Optional<ReleaseOrderDetail> releaseOrderDetail =
                        releaseOrderDetailRepository.findById(VALID_RELEASE_ORDER_DETAIL_ID);

                Item item = releaseOrderDetail.get().getObtainOrderDetail().getItem();

                Double beforeQuantity = item.getQuantity();

                List<ReleaseOrderDetail> result = releaseOrderDetailService.updateReleaseOrderDetails(list);

                Double afterQuantity = item.getQuantity();

                assertThat(result.get(0).getQuantity()).isEqualTo(list.get(0).getQuantity());
                assertThat(afterQuantity - beforeQuantity).isEqualTo(QUANTITY - UPDATE_QUANTITY);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_출고상세가_없는_경우 {
            private List<ReleaseOrderDetailUpdateRequest> list;

            @BeforeEach
            void setUp() {
                ReleaseOrderDetailUpdateRequest request = new ReleaseOrderDetailUpdateRequest();
                request.setId(INVALID_RELEASE_ORDER_DETAIL_ID);

                list = List.of(request);
            }

            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> releaseOrderDetailService.updateReleaseOrderDetails(list))
                        .isInstanceOf(ReleaseOrderDetailNotFoundException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_출고개수가_품목개수보다_많은_경우 {
            private List<ReleaseOrderDetailUpdateRequest> list;

            @BeforeEach
            void setUp() {
                ReleaseOrderDetailUpdateRequest request = new ReleaseOrderDetailUpdateRequest();
                request.setId(VALID_RELEASE_ORDER_DETAIL_ID);
                request.setQuantity(1_004.0);

                list = List.of(request);
            }

            @Test
            @DisplayName("예외를 던진다")
            void 예외를_던진다() {
                assertThatThrownBy(() -> releaseOrderDetailService.updateReleaseOrderDetails(list))
                        .isInstanceOf(ItemNotEnoughException.class);
            }
        }
    }
}
