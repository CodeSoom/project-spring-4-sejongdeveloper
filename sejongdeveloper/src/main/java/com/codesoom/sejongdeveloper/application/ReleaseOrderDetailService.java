package com.codesoom.sejongdeveloper.application;

import com.codesoom.sejongdeveloper.dto.ReleaseOrderDetailSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReleaseOrderDetailService {

    public void saveReleaseOrderDetails(List<ReleaseOrderDetailSaveRequest> releaseOrderDetails) {

    }
}
