package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.dto.ReleaseOrderSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReleaseOrderService {

    public Long saveReleaseOrder(ReleaseOrderSaveRequest request) {
        return null;
    }
}
