package com.codesoom.sejongdeveloper.controllers;

import com.codesoom.sejongdeveloper.dto.ReleaseOrderSaveRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@DisplayName("ReleaseOrderController 클래스")
@WebMvcTest(ReleaseOrderController.class)
class ReleaseOrderControllerTest {

    private static final String RELEASE_ORDER_NAME = "출고명";
    private static final LocalDate RELEASE_ORDER_DATE = LocalDate.of(2022,2,7);

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        //Object Mapper 사용할 때 LocalDate 이슈 해결
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Nested
    class 출고저장_요청을_처리하는_핸들러는 {
        @Nested
        class 유효성_검사를_통과한_경우 {

            private String validParam = null;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                ReleaseOrderSaveRequest request = ReleaseOrderSaveRequest.builder()
                        .name(RELEASE_ORDER_NAME)
                        .date(RELEASE_ORDER_DATE)
                        .build();

                validParam = objectMapper.writeValueAsString(request);
            }

            @Test
            void 회원을_저장한다() throws Exception {
                mockMvc.perform(post("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validParam))
                        .andExpect(status().isCreated());
            }
        }

        @Nested
        class 유효성_검사를_통과하지_못한_경우 {
            private String invalidParam = null;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                ReleaseOrderSaveRequest request = ReleaseOrderSaveRequest.builder()
                        .date(RELEASE_ORDER_DATE)
                        .build();

                invalidParam = objectMapper.writeValueAsString(request);
            }

            @Test
            void 에러코드로_응답한다() throws Exception {
                mockMvc.perform(post("/release-orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidParam))
                        .andExpect(status().isBadRequest());
            }
        }
    }

}