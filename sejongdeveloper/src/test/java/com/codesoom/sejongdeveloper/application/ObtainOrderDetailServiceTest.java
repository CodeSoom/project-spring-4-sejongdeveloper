package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.domain.ObtainOrderDetail;
import com.codesoom.sejongdeveloper.repository.ObtainOrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ObtainOrderDetailServiceTest {

    private ObtainOrderDetailService obtainOrderDetailService;

    private ObtainOrderDetailRepository obtainOrderDetailRepository = mock(ObtainOrderDetailRepository.class);

    @BeforeEach
    void setUp() {
        obtainOrderDetailService = new ObtainOrderDetailService(obtainOrderDetailRepository);
    }

    @Test
    void createObtainOrderDetails() {
        List<ObtainOrderDetail> obtainOrderDetails = new ArrayList<>();

        obtainOrderDetailService.createObtainOrderDetails(obtainOrderDetails);

        verify(obtainOrderDetailRepository).saveAll(any(List.class));
    }

}