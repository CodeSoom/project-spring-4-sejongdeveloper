package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.application.ObtainOrderService;
import com.codesoom.sejongdeveloper.dto.ObtainOrderDetailRequest;
import com.codesoom.sejongdeveloper.dto.ObtainOrderRequest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ObtainOrderController.class)
class ObtainOrderControllerTest {

    private static final String OBTAIN_ORDER_NAME = "수주명 테스트";
    private static final Long OBTAIN_ORDER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ObtainOrderService obtainOrderService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        given(obtainOrderService.save(any(ObtainOrderRequest.class))).willReturn(OBTAIN_ORDER_ID);
    }

    @Test
    void createValidRequest() throws Exception {
        ObtainOrderRequest obtainOrderRequest = getObtainOrderRequest();
        String json = objectMapper.writeValueAsString(obtainOrderRequest);

        mockMvc.perform(post("/obtain-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(obtainOrderService).save(any(ObtainOrderRequest.class));
    }

    private ObtainOrderRequest getObtainOrderRequest() {
        ObtainOrderDetailRequest obtainOrderDetailRequest = getObtainOrderDetailRequest();

        return ObtainOrderRequest.builder()
                .name(OBTAIN_ORDER_NAME)
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