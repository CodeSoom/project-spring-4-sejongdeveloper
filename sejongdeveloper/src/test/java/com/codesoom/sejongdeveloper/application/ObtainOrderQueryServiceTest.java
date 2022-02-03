package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.dto.ObtainOrderResponse;
import com.codesoom.sejongdeveloper.dto.ObtainOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ObtainOrderQueryServiceTest {


    private static final String OBTAIN_ORDER_NAME = "수주명";

    @Autowired
    private ObtainOrderQueryService obtainOrderQueryService;

    @Autowired
    private ObtainOrderRepository obtainOrderRepository;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            String name = OBTAIN_ORDER_NAME + i;
            LocalDate date = LocalDate.now().plusDays(i);

            ObtainOrder obtainOrder = getObtainOrder(name, date);
            obtainOrderRepository.save(obtainOrder);
        }
    }

    @DisplayName("수주 목록을 리턴한다.")
    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10);

        ObtainOrderSearchCondition condition = ObtainOrderSearchCondition.builder()
                .pageable(pageable)
                .build();

        Page<ObtainOrderResponse> page = obtainOrderQueryService.findAll(condition);

        assertThat(page.getTotalPages()).isEqualTo(1);
    }

    private ObtainOrder getObtainOrder(String name, LocalDate date) {
        return ObtainOrder.builder()
                .id(1L)
                .name(name)
                .date(date)
                .build();
    }

}