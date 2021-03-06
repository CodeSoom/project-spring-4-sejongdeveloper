package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.PlaceOrderQueryService;
import com.codesoom.sejongdeveloper.application.PlaceOrderService;
import com.codesoom.sejongdeveloper.application.UserService;
import com.codesoom.sejongdeveloper.domain.PlaceOrder;
import com.codesoom.sejongdeveloper.dto.PlaceOrderDetailUpdateRequest;
import com.codesoom.sejongdeveloper.dto.PlaceOrderResponse;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSaveRequest;
import com.codesoom.sejongdeveloper.dto.PlaceOrderSearchCondition;
import com.codesoom.sejongdeveloper.dto.PlaceOrderUpdateRequest;
import com.codesoom.sejongdeveloper.errors.PlaceOrderNotFoundException;
import com.codesoom.sejongdeveloper.repository.PlaceOrderRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaceOrderController.class)
@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("PlaceOrderController ?????????")
class PlaceOrderControllerTest {

    private static final String PLACE_ORDER_NAME = "testName";
    private static final Long VALID_PLACE_ORDER_ID = 1L;
    private static final Long INVALID_PLACE_ORDER_ID = 2L;
    private static final Long PLACE_ORDER_DETAIL_ID = 1L;
    private static final Long ITEM_ID = 1L;
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjF9.ZZ3CUl0jxeLGvQ1Js5nG2Ty5qGTlqai5ubDMXZOdaDk";
    private static final Cookie COOKIE = new Cookie("Authentication", TOKEN);

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private PlaceOrderService placeOrderService;

    @MockBean
    private PlaceOrderRepository placeOrderRepository;

    @MockBean
    private PlaceOrderQueryService placeOrderQueryService;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        given(placeOrderService.savePlaceOrder(any(PlaceOrderSaveRequest.class))).willReturn(VALID_PLACE_ORDER_ID);

        PlaceOrder placeOrder = PlaceOrder.builder()
                .id(VALID_PLACE_ORDER_ID)
                .build();

        given(placeOrderRepository.findById(VALID_PLACE_ORDER_ID)).willReturn(Optional.of(placeOrder));
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class ????????????_?????????_????????????_???????????? {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ?????????_???????????????_?????????_?????????_?????????_?????? {
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                PlaceOrderSaveRequest request = PlaceOrderSaveRequest.builder()
                        .name(PLACE_ORDER_NAME)
                        .build();

                json = objectMapper.writeValueAsString(request);
            }

            @Test
            @DisplayName("?????????_????????????")
            void ?????????_????????????() throws Exception {
                mockMvc.perform(post("/place-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(COOKIE))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("" + VALID_PLACE_ORDER_ID)));
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
                mockMvc.perform(get("/place-orders/" + VALID_PLACE_ORDER_ID)
                                .cookie(COOKIE))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("\"id\":" + VALID_PLACE_ORDER_ID)));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ?????????_????????????_?????????_??????_??????_?????? {
            @Test
            @DisplayName("Bad Request??? ????????????")
            void Bad_Request???_????????????() throws Exception {
                mockMvc.perform(get("/place-orders/" + INVALID_PLACE_ORDER_ID)
                                .cookie(COOKIE))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class ????????????_?????????_????????????_???????????? {
        private static final String UPDATE_PLACE_ORDER_NAME = "????????? ?????????";

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ?????????_???????????????_??????????????????_?????????_?????? {
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                PlaceOrderDetailUpdateRequest detailRequest = PlaceOrderDetailUpdateRequest.builder()
                        .id(PLACE_ORDER_DETAIL_ID)
                        .itemId(ITEM_ID)
                        .build();

                List<PlaceOrderDetailUpdateRequest> detailRequests = List.of(detailRequest);

                PlaceOrderUpdateRequest request = PlaceOrderUpdateRequest.builder()
                        .name(UPDATE_PLACE_ORDER_NAME)
                        .placeOrderDetails(detailRequests)
                        .build();

                json = objectMapper.writeValueAsString(request);
            }

            @Test
            @DisplayName("????????? ????????????")
            void ?????????_????????????() throws Exception {
                mockMvc.perform(patch("/place-orders/" + VALID_PLACE_ORDER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(COOKIE))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ????????????_??????_?????? {
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                PlaceOrderUpdateRequest request = PlaceOrderUpdateRequest.builder().build();

                json = objectMapper.writeValueAsString(request);
            }

            @Test
            @DisplayName("Bad Request??? ????????????")
            void Bad_Request???_????????????() throws Exception {
                mockMvc.perform(patch("/place-orders/" + VALID_PLACE_ORDER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(COOKIE))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ?????????_????????????_??????_?????? {
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                PlaceOrderDetailUpdateRequest detailRequest = PlaceOrderDetailUpdateRequest.builder()
                        .id(PLACE_ORDER_DETAIL_ID)
                        .build();

                List<PlaceOrderDetailUpdateRequest> detailRequests = List.of(detailRequest);

                PlaceOrderUpdateRequest request = PlaceOrderUpdateRequest.builder()
                        .name(UPDATE_PLACE_ORDER_NAME)
                        .placeOrderDetails(detailRequests)
                        .build();

                json = objectMapper.writeValueAsString(request);

                doThrow(new PlaceOrderNotFoundException(INVALID_PLACE_ORDER_ID))
                        .when(placeOrderService).updatePlaceOrder(
                                eq(INVALID_PLACE_ORDER_ID),
                                any(PlaceOrderUpdateRequest.class)
                        );

            }

            @Test
            @DisplayName("Bad Request??? ????????????")
            void Bad_Request???_????????????() throws Exception {
                mockMvc.perform(patch("/place-orders/" + INVALID_PLACE_ORDER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(COOKIE))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ???????????????_??????_?????? {
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                PlaceOrderUpdateRequest request = PlaceOrderUpdateRequest.builder()
                        .name(UPDATE_PLACE_ORDER_NAME)
                        .build();

                json = objectMapper.writeValueAsString(request);
            }

            @Test
            @DisplayName("Bad Request??? ????????????")
            void Bad_Request???_????????????() throws Exception {
                mockMvc.perform(patch("/place-orders/" + VALID_PLACE_ORDER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(COOKIE))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class ????????????_?????????_????????????_???????????? {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ???????????????_????????????_???????????????_??????_?????? {
            PlaceOrderSearchCondition nameCondition;
            PlaceOrderSearchCondition dateCondition;
            private LocalDate PLACE_ORDER_DATE = LocalDate.now();

            @BeforeEach
            void setUp() {
                nameCondition = getCondition(PLACE_ORDER_NAME, null);
                dateCondition = getCondition(null, PLACE_ORDER_DATE);

                List<PlaceOrderResponse> content = List.of(getPlaceOrderResponse());

                given(placeOrderQueryService.search(any(PlaceOrderSearchCondition.class), any(Pageable.class)))
                        .willReturn(new PageImpl<>(content, getPageable(), content.size()));
            }

            private PlaceOrderResponse getPlaceOrderResponse() {
                return PlaceOrderResponse.builder()
                        .id(PLACE_ORDER_DETAIL_ID)
                        .name(PLACE_ORDER_NAME)
                        .date(PLACE_ORDER_DATE)
                        .build();
            }

            @Test
            @DisplayName("??????????????? ????????????")
            void ???????????????_????????????() throws Exception {
                String json = objectMapper.writeValueAsString(nameCondition);

                mockMvc.perform(get("/place-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(COOKIE))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(PLACE_ORDER_NAME)));

                json = objectMapper.writeValueAsString(dateCondition);

                mockMvc.perform(get("/place-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(COOKIE))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(PLACE_ORDER_DATE.toString())));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ???????????????_????????????_???????????????_??????_?????? {
            private static final String NOT_PLACE_ORDER_NAME = "not name";
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                json = objectMapper.writeValueAsString(getCondition(NOT_PLACE_ORDER_NAME, null));

                given(placeOrderQueryService.search(any(PlaceOrderSearchCondition.class), any(Pageable.class)))
                        .willReturn(new PageImpl<>(new ArrayList<>(), getPageable(), 0));
            }

            @Test
            @DisplayName("???????????? ??????????????? ????????????")
            void ????????????_???????????????_????????????() throws Exception {
                mockMvc.perform(get("/place-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(COOKIE))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("[]")));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class ????????????_??????_???????????????_?????????_?????? {
            private String json;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                json = objectMapper.writeValueAsString(getCondition(null, null));

                given(placeOrderQueryService.search(any(PlaceOrderSearchCondition.class), any(Pageable.class)))
                        .willReturn(new PageImpl<>(new ArrayList<>(), getPageable(), 0));
            }

            @Test
            @DisplayName("???????????? ??????????????? ????????????")
            void ????????????_???????????????_????????????() throws Exception {
                mockMvc.perform(get("/place-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .cookie(COOKIE))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("[]")));
            }
        }

        private PlaceOrderSearchCondition getCondition(String name, LocalDate date) {
            return PlaceOrderSearchCondition.builder()
                    .name(name)
                    .startDate(LocalDate.now().minusDays(1))
                    .endDate(LocalDate.now())
                    .build();
        }

        private Pageable getPageable() {
            return PageRequest.of(0, 10);
        }
    }
}
