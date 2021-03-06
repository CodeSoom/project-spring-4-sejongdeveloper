package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.ReleaseOrderQueryService;
import com.codesoom.sejongdeveloper.application.ReleaseOrderService;
import com.codesoom.sejongdeveloper.application.UserService;
import com.codesoom.sejongdeveloper.domain.ReleaseOrder;
import com.codesoom.sejongdeveloper.domain.User;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSaveRequest;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderSearchCondition;
import com.codesoom.sejongdeveloper.dto.ReleaseOrderUpdateRequest;
import com.codesoom.sejongdeveloper.interceptors.LoginInterceptor;
import com.codesoom.sejongdeveloper.repository.ReleaseOrderRepository;
import com.codesoom.sejongdeveloper.repository.UserRepository;
import com.codesoom.sejongdeveloper.utils.JwtUtil;
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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ReleaseOrderController ?????????")
@WebMvcTest(ReleaseOrderController.class)
class ReleaseOrderControllerTest {

    private static final String RELEASE_ORDER_NAME = "?????????";
    private static final LocalDate RELEASE_ORDER_DATE = LocalDate.of(2022, 2, 7);
    private static final Long OBTAIN_ORDER__DETAIL_ID = 1L;
    private static final Long INVALID_RELEASE_ORDER_ID = 2L;
    private Long VALID_RELEASE_ORDER_ID = 1L;
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private ReleaseOrderService releaseOrderService;

    @MockBean
    private ReleaseOrderRepository releaseOrderRepository;

    @MockBean
    private ReleaseOrderQueryService releaseOrderQueryService;

    @MockBean
    private UserService userService;

    private Cookie cookie = new Cookie("Authentication", TOKEN);

    @BeforeEach
    void setUp() {
        //Object Mapper ????????? ??? LocalDate ?????? ??????
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        ReleaseOrder releaseOrder = ReleaseOrder.builder()
                .id(VALID_RELEASE_ORDER_ID)
                .build();

        given(releaseOrderRepository.findById(eq(VALID_RELEASE_ORDER_ID))).willReturn(Optional.of(releaseOrder));

        given(releaseOrderService.updateReleaseOrder(eq(VALID_RELEASE_ORDER_ID), any(ReleaseOrderUpdateRequest.class)))
                .willReturn(releaseOrder);

        User user = User.builder().build();

        given(userService.findUser(TOKEN)).willReturn(user);
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class ????????????_?????????_????????????_???????????? {
        @Nested
        class ?????????_?????????_?????????_?????? {

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
            @DisplayName("????????? ????????????")
            void ?????????_????????????() throws Exception {
                mockMvc.perform(post("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validParam)
                                .cookie(cookie))
                        .andExpect(status().isCreated());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ????????????_??????_??????_?????? {
            private String invalidName = null;  //???????????? ?????? ?????????

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
            @DisplayName("Bad Request??? ????????????")
            void Bad_Request???_????????????() throws Exception {
                mockMvc.perform(post("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidName)
                                .cookie(cookie))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ???????????????_??????_??????_?????? {

            private String invalidDetail = null;    //??????????????? ?????? ?????? ??????

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
            @DisplayName("Bad Request??? ????????????")
            void Bad_Request???_????????????() throws Exception {
                mockMvc.perform(post("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidDetail)
                                .cookie(cookie))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class ????????????_?????????_????????????_???????????? {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ?????????_????????????_?????????_??????_?????? {
            @Test
            @DisplayName("????????? ????????????")
            void ?????????_????????????() throws Exception {
                mockMvc.perform(get("/release-orders/" + VALID_RELEASE_ORDER_ID)
                                .cookie(cookie))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("\"id\":" + VALID_RELEASE_ORDER_ID)));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ?????????_????????????_?????????_??????_?????? {
            @Test
            @DisplayName("Bad Request??? ????????????")
            void Bad_Request???_????????????() throws Exception {
                mockMvc.perform(get("/release-orders/" + INVALID_RELEASE_ORDER_ID)
                                .cookie(cookie))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class ????????????_?????????_????????????_???????????? {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ?????????_????????????_?????????_??????_?????? {
            ReleaseOrderUpdateRequest request;
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                request = ReleaseOrderUpdateRequest.builder()
                        .name(RELEASE_ORDER_NAME + "??????")
                        .build();

                json = objectMapper.writeValueAsString(request);
            }


            @Test
            @DisplayName("????????? ????????????")
            void ?????????_????????????() throws Exception {
                mockMvc.perform(patch("/release-orders/" + VALID_RELEASE_ORDER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(cookie))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ?????????_????????????_?????????_??????_?????? {
            private String json;

            @BeforeEach
            void setUp() {
                json = "";
            }

            @Test
            @DisplayName("Bad Request??? ????????????")
            void Bad_Request???_????????????() throws Exception {
                mockMvc.perform(patch("/release-orders/" + INVALID_RELEASE_ORDER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(cookie))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class ??????????????????_?????????_????????????_???????????? {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ?????????_????????????_?????????_??????_?????? {
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                ReleaseOrderSearchCondition condition = ReleaseOrderSearchCondition.builder()
                        .name(RELEASE_ORDER_NAME)
                        .build();

                json = objectMapper.writeValueAsString(condition);
            }

            @Test
            @DisplayName("??????????????? ????????????")
            void ???????????????_????????????() throws Exception {
                mockMvc.perform(get("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(cookie))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ?????????_???????????????_?????????_??????_?????? {
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                ReleaseOrderSearchCondition condition = ReleaseOrderSearchCondition.builder()
                        .startDate(LocalDate.now().minusDays(7))
                        .endDate(LocalDate.now())
                        .build();

                json = objectMapper.writeValueAsString(condition);
            }

            @Test
            @DisplayName("??????????????? ????????????")
            void ???????????????_????????????() throws Exception {
                mockMvc.perform(get("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(cookie))
                        .andExpect(status().isOk());
            }
        }
    }
}
