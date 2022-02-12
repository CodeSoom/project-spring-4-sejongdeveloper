package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.domain.ReleaseOrderDetail;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ReleaseOrderController 클래스")
@WebMvcTest(ReleaseOrderDetailController.class)
class ReleaseOrderDetailControllerTest {

    private static final Long VALID_RELEASE_ORDER_ID = 1L;
    private static final Long VALID_RELEASE_ORDER_DETAIL_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReleaseOrderDetailRepository releaseOrderDetailRepository;

    @BeforeEach
    void setUp() {
        ReleaseOrderDetail releaseOrderDetail = new ReleaseOrderDetail(VALID_RELEASE_ORDER_DETAIL_ID);

        List<ReleaseOrderDetail> list = List.of(releaseOrderDetail);

        given(releaseOrderDetailRepository.findAllByReleaseOrderId(eq(VALID_RELEASE_ORDER_ID))).willReturn(list);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 출고상세조회_요청을_처리하는_핸들러는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_출고상세_있는_경우 {
            @Test
            @DisplayName("출고상세를 리턴한다")
            void 출고상세를_리턴한다() throws Exception {
                mockMvc.perform(get("/release-order-details/release-orders/" + VALID_RELEASE_ORDER_ID))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("\"id\":" + VALID_RELEASE_ORDER_DETAIL_ID)));
            }
        }
    }
}
