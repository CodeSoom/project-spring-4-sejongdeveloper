package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.ObtainOrderService;
import com.codesoom.sejongdeveloper.domain.Item;
import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.dto.ObtainOrderDetailRequest;
import com.codesoom.sejongdeveloper.dto.ObtainOrderRequest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ObtainOrderController.class)
class ObtainOrderControllerTest {

    private static final String OBTAIN_ORDER_NAME = "수주명 테스트";
    private static final Long OBTAIN_ORDER_ID = 1L;
    private static final Long ITEM_ID = 1L;
    private static final Long INVALID_ITEM_ID = 2L;
    private static final String UPDATE_OBTAIN_ORDER_NAME = "수정한 수주명 테스트";

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

        given(obtainOrderService.createObtainOrder(any(ObtainOrder.class), any(List.class))).willReturn(OBTAIN_ORDER_ID);

        Item item = Item.builder()
                .id(ITEM_ID)
                .build();
        given(itemRepository.findById(ITEM_ID)).willReturn(Optional.of(item));
    }

    @Test
    void createValidRequest() throws Exception {
        ObtainOrderRequest obtainOrderRequest = getObtainOrderRequest(OBTAIN_ORDER_NAME);
        String json = objectMapper.writeValueAsString(obtainOrderRequest);

        mockMvc.perform(post("/obtain-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(obtainOrderService).createObtainOrder(any(ObtainOrder.class), any(List.class));
    }

    @Test
    void createInValidRequest() throws Exception {
        ObtainOrderRequest obtainOrderRequest = getInvalidName();
        String json = objectMapper.writeValueAsString(obtainOrderRequest);

        mockMvc.perform(post("/obtain-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        obtainOrderRequest = getIsNullObtainOrderDetails();
        json = objectMapper.writeValueAsString(obtainOrderRequest);

        mockMvc.perform(post("/obtain-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        obtainOrderRequest = getInvalidItem();
        json = objectMapper.writeValueAsString(obtainOrderRequest);

        mockMvc.perform(post("/obtain-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

    }

    @Test
    void createWrongItem() throws Exception {
        ObtainOrderDetailRequest obtainOrderDetailRequest = ObtainOrderDetailRequest.builder()
                .itemId(INVALID_ITEM_ID)
                .quantity(new BigDecimal(1_000))
                .build();

        ObtainOrderRequest obtainOrderRequest = ObtainOrderRequest.builder()
                .name(OBTAIN_ORDER_NAME)
                .obtainOrderDetails(List.of(obtainOrderDetailRequest))
                .build();

        String json = objectMapper.writeValueAsString(obtainOrderRequest);

        mockMvc.perform(post("/obtain-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateValidRequest() throws Exception {
        ObtainOrderRequest obtainOrderRequest = getObtainOrderRequest(UPDATE_OBTAIN_ORDER_NAME);
        String json = objectMapper.writeValueAsString(obtainOrderRequest);

        mockMvc.perform(patch("/obtain-orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(obtainOrderService).updateObtainOrder(eq(OBTAIN_ORDER_ID), any(ObtainOrder.class), any(List.class));
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