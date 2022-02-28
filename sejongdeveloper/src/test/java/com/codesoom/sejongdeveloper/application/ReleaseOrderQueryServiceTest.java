package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.domain.User;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderResponse;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderRepository;
import com.codesoom.sejongdeveloper.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


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

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class search_메소드는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_출고명의_출고를_찾은_경우 {
            private ReleaseOrderSearchCondition condition;
            private Pageable pageable;

            @BeforeEach
            void setUp() {
                ReleaseOrder releaseOrder = ReleaseOrder.builder()
                        .name(RELEASE_ORDER_NAME)
                        .build();

                releaseOrderRepository.save(releaseOrder);

                condition = ReleaseOrderSearchCondition.builder()
                        .name(releaseOrder.getName())
                        .build();

                pageable = PageRequest.of(0, 10);
            }

            @Test
            @DisplayName("출고 목록을 리턴한다")
            void 출고_목록을_리턴한다() {
                Page<ReleaseOrderResponse> page = releaseOrderQueryService.search(condition, pageable);

                assertThat(page.getContent().size()).isEqualTo(1);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_출고일의_출고를_찾은_경우 {
            private ReleaseOrderSearchCondition condition;
            private Pageable pageable;

            @BeforeEach
            void setUp() {
                ReleaseOrder releaseOrder = ReleaseOrder.builder()
                        .date(LocalDate.now())
                        .build();

                releaseOrderRepository.save(releaseOrder);

                condition = ReleaseOrderSearchCondition.builder()
                        .startDate(LocalDate.now().minusDays(7))
                        .startDate(LocalDate.now())
                        .build();

                pageable = PageRequest.of(0, 10);
            }

            @Test
            @DisplayName("출고 목록을 리턴한다")
            void 출고_목록을_리턴한다() {
                Page<ReleaseOrderResponse> page = releaseOrderQueryService.search(condition, pageable);

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

                condition = ReleaseOrderSearchCondition.builder()
                        .name(releaseOrder.getName() + 1004)
                        .build();

                pageable = PageRequest.of(0, 10);
            }

            @Test
            @DisplayName("비어있는 목록을 리턴한다")
            void 비어있는_목록을_리턴한다() {
                Page<ReleaseOrderResponse> page = releaseOrderQueryService.search(condition, pageable);

                assertThat(page.getContent().size()).isEqualTo(0);
            }
        }
    }
}
