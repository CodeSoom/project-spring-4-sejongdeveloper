package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderResponse;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ReleaseOrderQueryService 클래스")
class ReleaseOrderQueryServiceTest {

    private static final String RELEASE_ORDER_NAME = "출고명";

    @Autowired
    private ReleaseOrderQueryService releaseOrderQueryService;

    @Autowired
    private ReleaseOrderRepository releaseOrderRepository;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class search_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 일치하는_검색조건이_있는_경우 {
            private ReleaseOrderSearchCondition condition;
            private Pageable pageable;

            @BeforeEach
            void setUp() {
                ReleaseOrder releaseOrder = ReleaseOrder.builder()
                        .name(RELEASE_ORDER_NAME)
                        .build();

                releaseOrderRepository.save(releaseOrder);

                condition = new ReleaseOrderSearchCondition();
                condition.setName(releaseOrder.getName());
                condition.setPageable(PageRequest.of(0, 10));
            }

            @Test
            @DisplayName("출고 목록을 리턴한다")
            void 출고_목록을_리턴한다() {
                Page<ReleaseOrderResponse> page = releaseOrderQueryService.search(condition);

                assertThat(page.getContent().size()).isEqualTo(1);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 일치하는_검색조건이_없는_경우 {
            private ReleaseOrderSearchCondition condition;
            private Pageable pageable;

            @BeforeEach
            void setUp() {
                ReleaseOrder releaseOrder = ReleaseOrder.builder()
                        .name(RELEASE_ORDER_NAME)
                        .build();

                releaseOrderRepository.save(releaseOrder);

                condition = new ReleaseOrderSearchCondition();
                condition.setName(releaseOrder.getName() + 1004);
                condition.setPageable(PageRequest.of(0, 10));
            }

            @Test
            @DisplayName("비어있는 목록을 리턴한다")
            void 비어있는_목록을_리턴한다() {
                Page<ReleaseOrderResponse> page = releaseOrderQueryService.search(condition);

                assertThat(page.getContent().size()).isEqualTo(0);
            }
        }
    }
}
