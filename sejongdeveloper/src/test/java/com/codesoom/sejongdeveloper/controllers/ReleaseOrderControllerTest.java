package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.ReleaseOrderQueryService;
import com.codesoom.sejongdeveloper.application.ReleaseOrderService;
import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSearchCondition;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderUpdateRequest;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderRepository;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ReleaseOrderController 클래스")
@WebMvcTest(ReleaseOrderController.class)
class ReleaseOrderControllerTest {

    private static final String RELEASE_ORDER_NAME = "출고명";
    private static final LocalDate RELEASE_ORDER_DATE = LocalDate.of(2022, 2, 7);
    private static final Long OBTAIN_ORDER__DETAIL_ID = 1L;
    private static final Long INVALID_RELEASE_ORDER_ID = 2L;
    private Long VALID_RELEASE_ORDER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private ReleaseOrderService releaseOrderService;

    @MockBean
    private ReleaseOrderRepository releaseOrderRepository;

    @MockBean
    private ReleaseOrderQueryService releaseOrderQueryService;

    @BeforeEach
    void setUp() {
        //Object Mapper 사용할 때 LocalDate 이슈 해결
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        ReleaseOrder releaseOrder = ReleaseOrder.builder()
                .id(VALID_RELEASE_ORDER_ID)
                .build();

        given(releaseOrderRepository.findById(eq(VALID_RELEASE_ORDER_ID))).willReturn(Optional.of(releaseOrder));

        given(releaseOrderService.updateReleaseOrder(eq(VALID_RELEASE_ORDER_ID), any(ReleaseOrderUpdateRequest.class)))
                .willReturn(releaseOrder);
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
                        .quantity(1_000.0)
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
            @DisplayName("Bad Request로 응답한다")
            void Bad_Request로_응답한다() throws Exception {
                mockMvc.perform(post("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidName))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 출고상세를_입력_못한_경우 {

            private String invalidDetail = null;    //출고상세를 입력 못한 요청

            @BeforeEach
            void setUp() throws JsonProcessingException {
                invalidDetail = getJson();
            }

            private String getJson() throws JsonProcessingException {
                ReleaseOrderSaveRequest request = getParam();
                return objectMapper.writeValueAsString(request);
            }

            private ReleaseOrderSaveRequest getParam() {
                return ReleaseOrderSaveRequest.builder()
                        .name(RELEASE_ORDER_NAME)
                        .date(RELEASE_ORDER_DATE)
                        .build();
            }

            @Test
            @DisplayName("Bad Request로 응답한다")
            void Bad_Request로_응답한다() throws Exception {
                mockMvc.perform(post("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidDetail))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 출고조회_요청을_처리하는_핸들러는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_출고가_있는_경우 {
            @Test
            @DisplayName("출고를 리턴한다")
            void 출고를_리턴한다() throws Exception {
                mockMvc.perform(get("/release-orders/" + VALID_RELEASE_ORDER_ID))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("\"id\":" + VALID_RELEASE_ORDER_ID)));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_출고가_없는_경우 {
            @Test
            @DisplayName("Bad Request로 응답한다")
            void Bad_Request로_응답한다() throws Exception {
                mockMvc.perform(get("/release-orders/" + INVALID_RELEASE_ORDER_ID))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 출고수정_요청을_처리하는_핸들러는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_출고가_있는_경우 {
            ReleaseOrderUpdateRequest request;
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                request = new ReleaseOrderUpdateRequest();
                request.setName(RELEASE_ORDER_NAME + "수정");

                json = objectMapper.writeValueAsString(request);
            }


            @Test
            @DisplayName("출고를 리턴한다")
            void 출고를_리턴한다() throws Exception {
                mockMvc.perform(patch("/release-orders/" + VALID_RELEASE_ORDER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 주어진_아이디의_출고가_없는_경우 {
            private String json;

            @BeforeEach
            void setUp() {
                json = "";
            }

            @Test
            @DisplayName("Bad Request로 응답한다")
            void Bad_Request로_응답한다() throws Exception {
                mockMvc.perform(patch("/release-orders/" + INVALID_RELEASE_ORDER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 출고목록조회_요청을_처리하는_핸들러는 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 유효한_파라미터인_경우 {
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                ReleaseOrderSearchCondition condition = new ReleaseOrderSearchCondition();
                condition.setName(RELEASE_ORDER_NAME);

                json = objectMapper.writeValueAsString(condition);
            }

            @Test
            @DisplayName("출고목록을 리턴한다")
            void 출고목록을_리턴한다() throws Exception {
                mockMvc.perform(get("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                        .andExpect(status().isOk());
            }
        }
    }
}
