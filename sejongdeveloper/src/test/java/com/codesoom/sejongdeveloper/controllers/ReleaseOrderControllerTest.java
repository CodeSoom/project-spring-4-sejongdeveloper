package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSaveRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ReleaseOrderController 클래스")
@WebMvcTest(ReleaseOrderController.class)
class ReleaseOrderControllerTest {

    private static final String RELEASE_ORDER_NAME = "출고명";
    private static final LocalDate RELEASE_ORDER_DATE = LocalDate.of(2022,2,7);
    private static final Long OBTAIN_ORDER__DETAIL_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        //Object Mapper 사용할 때 LocalDate 이슈 해결
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 출고저장_요청을_처리하는_핸들러는 {
        @Nested
        class 유효성_검사를_통과한_경우 {

            private String validParam = null;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                validParam = getJson();
            }

            private String getJson() throws JsonProcessingException {
                return objectMapper.writeValueAsString(getParam());
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
            @DisplayName("회원을 저장한다")
            void 회원을_저장한다() throws Exception {
                mockMvc.perform(post("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validParam))
                        .andExpect(status().isCreated());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 출고명을_입력_못한_경우 {
            private String invalidName = null;  //유효하지 않는 출고명

            @BeforeEach
            void setUp() throws JsonProcessingException {
                invalidName = getJson();
            }

            private String getJson() throws JsonProcessingException {
                ReleaseOrderSaveRequest request = getParam();
                return objectMapper.writeValueAsString(request);
            }

            private ReleaseOrderSaveRequest getParam() {
                return ReleaseOrderSaveRequest.builder()
                        .date(RELEASE_ORDER_DATE)
                        .build();
            }

            @Test
            @DisplayName("에러코드로 응답한다")
            void 에러코드로_응답한다() throws Exception {
                mockMvc.perform(post("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidName))
                        .andExpect(status().isBadRequest());
            }
        }
    }

}