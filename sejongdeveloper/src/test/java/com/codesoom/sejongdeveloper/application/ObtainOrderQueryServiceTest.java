package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrder;
import com.codesoom.sejongdeveloper.dto.ObtainOrderResponse;
import com.codesoom.sejongdeveloper.dto.ObtainOrderSearchCondition;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import com.codesoom.sejongdeveloper.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ObtainOrderQueryServiceTest {

    private static final String OBTAIN_ORDER_NAME = "수주명";

    @Autowired
    private ObtainOrderQueryService obtainOrderQueryService;

    @Autowired
    private ObtainOrderRepository obtainOrderRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        List<ObtainOrder> obtainOrders = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            String name = OBTAIN_ORDER_NAME + i;
            LocalDate date = LocalDate.now().plusDays(i);

            obtainOrders.add(getObtainOrder(name, date)) ;
        }

        obtainOrderRepository.saveAll(obtainOrders);
    }

    @DisplayName("수주 목록을 리턴한다.")
    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10);

        ObtainOrderSearchCondition condition = ObtainOrderSearchCondition.builder().build();

        Page<ObtainOrderResponse> page = obtainOrderQueryService.findObtainOrders(condition, pageable);

        assertThat(page.getContent().size()).isEqualTo(10);
    }

    private ObtainOrder getObtainOrder(String name, LocalDate date) {
        return ObtainOrder.builder()
                .name(name)
                .date(date)
                .build();
    }
}
