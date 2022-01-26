package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.dto.ObtainOrderRequest;
import com.codesoom.sejongdeveloper.repository.ObtainOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ObtainOrderService {

    private ObtainOrderRepository obtainOrderRepository;

    @Transactional
    public Long save(ObtainOrderRequest obtainOrderRequest) {
        return null;
    }
}
