package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.ObtainOrderService;
import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.dto.ObtainOrderDetailRequest;
import com.codesoom.sejongdeveloper.dto.ObtainOrderRequest;
import com.codesoom.sejongdeveloper.dto.ObtainOrderResponse;
import com.codesoom.sejongdeveloper.errors.ObtainOrderNotFoundException;
import com.codesoom.sejongdeveloper.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ObtainOrderController.class)
class ObtainOrderControllerTest {

    private static final String OBTAIN_ORDER_NAME = "수주명 테스트";
    private static final Long OBTAIN_ORDER_ID = 1L;
    private static final Long ITEM_ID = 1L;
    private static final Long INVALID_ITEM_ID = 2L;
    private static final String UPDATE_OBTAIN_ORDER_NAME = "수정한 수주명 테스트";
    private static final Long INVALID_OBTAIN_ORDER_ID = 2L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ObtainOrderService obtainOrderService;

    @MockBean
    private ItemRepository itemRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        ObtainOrderResponse obtainOrderResponse = ObtainOrderResponse.builder()
                .id(OBTAIN_ORDER_ID)
                .build();

        given(obtainOrderService.getObtainOrder(OBTAIN_ORDER_ID)).willReturn(obtainOrderResponse);

        given(obtainOrderService.getObtainOrder(INVALID_OBTAIN_ORDER_ID))
                .willThrow(new ObtainOrderNotFoundException(INVALID_OBTAIN_ORDER_ID));

        given(obtainOrderService.createObtainOrder(
                any(ObtainOrder.class),
                anyList())
        ).willReturn(OBTAIN_ORDER_ID);

        given(obtainOrderService.updateObtainOrder(
                eq(INVALID_OBTAIN_ORDER_ID),
                any(ObtainOrder.class),
                anyList())
        ).willThrow(new ObtainOrderNotFoundException(INVALID_OBTAIN_ORDER_ID));

        Item item = Item.builder()
                .id(ITEM_ID)
                .build();

        given(itemRepository.findById(ITEM_ID)).willReturn(Optional.of(item));
    }

    @Test
    void createValidRequest() throws Exception {
        String json = objectMapper.writeValueAsString(getObtainOrderRequest(OBTAIN_ORDER_NAME));

        mockMvc.perform(post("/obtain-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(obtainOrderService).createObtainOrder(any(ObtainOrder.class), anyList());
    }

    @Test
    void createInValidRequest() throws Exception {
        String json = objectMapper.writeValueAsString(getInvalidName());

        mockMvc.perform(post("/obtain-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        json = objectMapper.writeValueAsString(getIsNullObtainOrderDetails());

        mockMvc.perform(post("/obtain-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        json = objectMapper.writeValueAsString(getInvalidItem());

        mockMvc.perform(post("/obtain-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

    }

    @Test
    void createWrongItem() throws Exception {
        String json = objectMapper.writeValueAsString(getObtainOrderWithInvalidItemId());

        mockMvc.perform(post("/obtain-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateValidRequest() throws Exception {
        String json = objectMapper.writeValueAsString(getObtainOrderRequest(UPDATE_OBTAIN_ORDER_NAME));

        mockMvc.perform(patch("/obtain-orders/"+OBTAIN_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(obtainOrderService).updateObtainOrder(eq(OBTAIN_ORDER_ID), any(ObtainOrder.class), anyList());
    }

    @Test
    void updateWithoutObtainOrder() throws Exception {
        String json = objectMapper.writeValueAsString(getObtainOrderRequest(UPDATE_OBTAIN_ORDER_NAME));

        mockMvc.perform(patch("/obtain-orders/"+INVALID_OBTAIN_ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(obtainOrderService).updateObtainOrder(eq(INVALID_OBTAIN_ORDER_ID), any(ObtainOrder.class), anyList());
    }

    @Test
    void getObtainOrder() throws Exception {
        mockMvc.perform(get("/obtain-orders/" + OBTAIN_ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":" + OBTAIN_ORDER_ID)));

        verify(obtainOrderService).getObtainOrder(OBTAIN_ORDER_ID);
    }

    @Test
    void getObtainOrderInvalidId() throws Exception {
        mockMvc.perform(get("/obtain-orders/" + INVALID_OBTAIN_ORDER_ID))
                .andExpect(status().isBadRequest());
    }

    private ObtainOrderRequest getObtainOrderWithInvalidItemId() {
        ObtainOrderDetailRequest obtainOrderDetailRequest = ObtainOrderDetailRequest.builder()
                .itemId(INVALID_ITEM_ID)
                .quantity(new BigDecimal(1_000))
                .build();

        return ObtainOrderRequest.builder()
                .name(OBTAIN_ORDER_NAME)
                .obtainOrderDetails(List.of(obtainOrderDetailRequest))
                .build();
    }

    private ObtainOrderRequest getInvalidItem() {
        ObtainOrderDetailRequest obtainOrderDetailRequest = ObtainOrderDetailRequest.builder()
                .quantity(new BigDecimal(1_000))
                .build();

        return ObtainOrderRequest.builder()
                .name(OBTAIN_ORDER_NAME)
                .obtainOrderDetails(List.of(obtainOrderDetailRequest))
                .build();
    }

    private ObtainOrderRequest getIsNullObtainOrderDetails() {
        return ObtainOrderRequest.builder()
                .name(OBTAIN_ORDER_NAME)
                .build();
    }

    private ObtainOrderRequest getInvalidName() {
        return ObtainOrderRequest.builder()
                    .name("")
                    .build();
    }

    private ObtainOrderRequest getObtainOrderRequest(String name) {
        ObtainOrderDetailRequest obtainOrderDetailRequest = getObtainOrderDetailRequest();

        return ObtainOrderRequest.builder()
                .name(name)
                .obtainOrderDetails(List.of(obtainOrderDetailRequest))
                .build();
    }

    private ObtainOrderDetailRequest getObtainOrderDetailRequest() {
        return ObtainOrderDetailRequest.builder()
                .itemId(1L)
                .quantity(new BigDecimal(1_000))
                .build();
    }
}